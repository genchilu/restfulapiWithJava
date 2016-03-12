 var getUser=function(){
    var username = $('#username').val()
    $.ajax({
        url: "user/" + username,
        type:"GET",
        dataType:'text',
        success: function(msg){
            $('#output').append(msg)
        },
    });
}

 var postUser=function(){
    var username = $('#username').val()
    $.ajax({
        url: "user/" + username,
        type:"POST",
        dataType:'text',
        success: function(msg){
            $('#output').append(msg)
        },
    });
}

 var putUser=function(){
    var username = $('#username').val()
    $.ajax({
        url: "user/" + username,
        type:"PUT",
        dataType:'text',
        success: function(msg){
            $('#output').append(msg)
        },
    });
}

 var deleteUser=function(){
    var username = $('#username').val()
    $.ajax({
        url: "user/" + username,
        type:"DELETE",
        dataType:'text',
        success: function(msg){
            $('#output').append(msg)
        },
    });
}