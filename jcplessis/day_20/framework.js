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

function string_to_int_matrix(string){
    return string.split("\n").map(line => line.split("").map(Number));
}

function string_to_string_matrix(string){
    return string.split("\n").map(line => line.split(""));
}


function matrix_to_string(matrix){
    return matrix.map(line => line.join("")).join("\n");
}

function create_matrix(width, height, content=null){
    return Array(height).fill().map(()=>Array(width).fill(content));
}

function is_inside_matrix(x, y, matrix){
    return !(x < 0 || y < 0 || y >= matrix.length || x >= matrix[0].length);
}

