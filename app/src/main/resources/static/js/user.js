 var getuser=function(){
    var username = $('#username').val()
    var address = $('#address').val()
    var age = $('#age').val()
    //alert(username)
    $.ajax({
        url: "user/" + username,
        type:"GET",
        dataType:'text',
        success: function(msg){
            $('#output').append(msg)
        },
    });
    
}