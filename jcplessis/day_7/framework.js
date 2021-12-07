var EOL = "<br/>";

function sysout(...message){
    document.write(...message);
}

function assertEquals(explanation, expected, actual){
    if (expected == actual){
        sysout("<div class='ok'>", explanation, " - OK !</div>");
    }else{
        sysout("<div class='failed'>", explanation, " - FAILED ! Expected :", expected, " != Actual : ", actual, EOL);
        sysout((new Error()).stack);
        sysout("</div>");
    }
}

function assertObjectsEquals(explanation, expected, actual){
    assertEquals(explanation, JSON.stringify(expected), JSON.stringify(actual));
}