 var getUser=function(){
    var username = $('#username').val()
    $.ajax({
        url: "user/" + username,
        type:"GET",
        dataType:'json',
        success: function(msg){
            $('#output').append(" <p>" + JSON.stringify(msg) + "</p> ")
        },
    });
}

 var postUser=function(){
    var username = $('#username').val()
    var counrty = $('#country').val()
    var city = $('#city').val()
    $.ajax({
        url: "user/" + username,
        type:"POST",
        data: "country=" + counrty + "&city=" + city,
        dataType:'json',
        success: function(msg){
            $('#output').append(" <p>" + JSON.stringify(msg) + "</p> ")
        },
    });
}

 var putUser=function(){
    var username = $('#username').val()
    var counrty = $('#country').val()
    var city = $('#city').val()
    $.ajax({
        url: "user/" + username,
        type:"PUT",
        data: "country=" + counrty + "&city=" + city,
        dataType:'json',
        success: function(msg){
            $('#output').append(" <p>" + JSON.stringify(msg) + "</p> ")
        },
    });
}

 var deleteUser=function(){
    var username = $('#username').val()
    $.ajax({
        url: "user/" + username,
        type:"DELETE",
        dataType:'json',
        success: function(msg){
            $('#output').append(" <p>" + JSON.stringify(msg) + "</p> ")
        },
    });
}