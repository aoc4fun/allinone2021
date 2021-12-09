package org.rt.advent.twentyone.day8;

import java.util.*;

public class DigitGuess {
    enum Signal {
        a,b,c,d,e,f,g
    }

    ArrayList<EnumSet<Signal>> digits = new ArrayList<EnumSet<Signal>>(10);
    boolean signalGuessed=false;

    public DigitGuess() {
        for(int i=0;i<10;i++) {
            digits.add(EnumSet.noneOf(Signal.class));
        }
    }
    public void guessSignals(String[] uniqueSignals) {
        StoreSignalByLength storage = new StoreSignalByLength();
        Arrays.stream(uniqueSignals).forEach(storage::addSignal);

        thisIsAOne(getSignalBySize(2, storage));
        thisIsASeven(getSignalBySize(3, storage));
        thisIsAFour(getSignalBySize(4, storage));
        thisIsAHeight(getSignalBySize(7, storage));

        Collection<EnumSet<Signal>> twoThreeFive = storage.getSignalsFromSize(5);
        saveSignalAsNumber(findThreeOutOfTwoThreeFive(twoThreeFive), 3);
        twoThreeFive.remove(digits.get(3));

        Collection<EnumSet<Signal>> zeroSixNine = storage.getSignalsFromSize(6);
        saveSignalAsNumber(findNineOutOfZeroSixNine(digits.get(3), zeroSixNine),9);
        zeroSixNine.remove(digits.get(9));
        saveSignalAsNumber(findZeroOutOfZeroSix(digits.get(1), zeroSixNine), 0);
        zeroSixNine.remove(digits.get(0));
        saveSignalAsNumber(getSoloElementOrFail(zeroSixNine), 6);

        saveSignalAsNumber(findFiveOutOfTwoFive(digits.get(6), twoThreeFive), 5);
        twoThreeFive.remove(digits.get(5));

        saveSignalAsNumber(getSoloElementOrFail(twoThreeFive), 2);
        signalGuessed=true;

    }

    public int getSignalValue(String signal) {
        return getSignalValue(transFormToSet(signal));
    }
    public int getSignalValue(Collection<String> digitsSignals) {
        int result  = 0;
        for(String digitSignal : digitsSignals) {
            if(!digitSignal.isEmpty()) {
                result = result * 10;
                result += getSignalValue(digitSignal);
            }
        }
        return result;
    }

    public int getSignalValue(EnumSet<Signal> signal) {
        if(!signalGuessed) throw new IllegalStateException("signal should be guessed first");
        return this.digits.indexOf(signal);
    }

    protected EnumSet<Signal> getSoloElementOrFail(Collection<EnumSet<Signal>> result) {
        return result
                .stream()
                .reduce((a, b) -> {
                    throw new IllegalStateException("expects only one signal");
                })
                .orElseThrow(() -> new IllegalStateException("expects at least one signal"));
    }
    protected EnumSet<Signal> getSignalBySize(int size, StoreSignalByLength storage) {
        Collection<EnumSet<Signal>> result = storage.getSignalsFromSize(size);
        return getSoloElementOrFail(result);
    }

    protected void thisIsAOne(EnumSet<Signal> signal) {
        saveSignalAsNumber(signal,1);
    }
    protected void thisIsASeven(EnumSet<Signal> signal) {
        saveSignalAsNumber(signal,7);
    }
    protected void thisIsAHeight(EnumSet<Signal> signal) {
        saveSignalAsNumber(signal,8);
    }
    protected void thisIsAFour(EnumSet<Signal> signal) {
        saveSignalAsNumber(signal,4);
    }
    protected void saveSignalAsNumber(EnumSet<Signal> signal, int number) {
        digits.set(number, signal);
    }


    public static EnumSet<Signal> transFormToSet(String signal) {
        EnumSet<Signal> result = EnumSet.noneOf(Signal.class);
        signal.codePoints()
                .mapToObj(c -> Signal.valueOf(String.valueOf((char) c)))
                .forEach(result::add);
        return result;
    }

    protected EnumSet<Signal> findThreeOutOfTwoThreeFive(Collection<EnumSet<Signal>> signals) {
        SignalCount counter = new SignalCount();
        signals.forEach(counter::addSignals);
        return counter.getSignalsHavingAtLeast(2);
    }
    protected EnumSet<Signal> findNineOutOfZeroSixNine(Set<Signal> treeSignal, Collection<EnumSet<Signal>>  zeroSixNineSignals) {
        return zeroSixNineSignals.stream()
                .filter(s -> {
                    Set<Signal> test = s.clone();
                    test.removeAll(treeSignal);
                    return test.size()==1;
                }).findFirst().orElseThrow(() -> new IllegalArgumentException("I expect to find a result when intersecting 9 and 3"));
    }

    protected EnumSet<Signal> findZeroOutOfZeroSix(Set<Signal> oneSignal, Collection<EnumSet<Signal>> zeroSixSignals) {
        EnumSet<Signal> result = null;
        return zeroSixSignals.stream()
                .filter(s -> {
                    EnumSet<Signal> test = s.clone();
                    test.retainAll(oneSignal);
                    return test.size()==2;
                }).findFirst().orElseThrow(() -> new IllegalArgumentException("I expect to find a result when intersecting 0 and 1"));
    }

    protected EnumSet<Signal> findFiveOutOfTwoFive(Set<Signal> sixSignal, Collection<EnumSet<Signal>> twoOrFiveSignals) {
        EnumSet<Signal> result = null;
        return twoOrFiveSignals.stream()
                .filter(s -> {
                    EnumSet<Signal> test = s.clone();
                    test.retainAll(sixSignal);
                    return test.size()==5;
                }).findFirst().orElseThrow(() -> new IllegalArgumentException("I expect to find a result when intersecting 0 and 1"));
    }

    static class SignalCount {
        EnumMap<Signal, Integer> countOfSignal = new EnumMap<Signal, Integer>(Signal.class);

        public void addSignals(Collection<Signal> signals) {
            signals.forEach(s -> {
                int count = countOfSignal.computeIfAbsent(s, e -> 0);
                countOfSignal.put(s, ++count);
            });
        }

        public EnumSet<Signal> getSignalsHavingAtLeast(int minCount) {
            EnumSet result = EnumSet.noneOf(Signal.class);
            countOfSignal.forEach((s,count) -> {
                if(count>=minCount) {
                    result.add(s);
                }
            });
            return result;
        }
    }

    static class StoreSignalByLength {
        TreeMap<Integer, Collection<EnumSet<Signal>>> countOfSignal = new TreeMap<>();

        public void addSignal(String signal) {
            EnumSet<Signal> toStore =transFormToSet(signal);
            countOfSignal.computeIfAbsent(toStore.size(), ArrayList::new).add(toStore);
        }

        public Collection<EnumSet<Signal>> getSignalsFromSize(int size) {
            return countOfSignal.get(size);
        }

    }
}
