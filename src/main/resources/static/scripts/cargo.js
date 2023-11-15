function abrirModal(codigo){
    $('#form').removeClass('was-validated');
    $('#rolId').val(codigo)
    if(codigo != 0){
        $.ajax({
            url : "/Dashboard/Rol/" + codigo,
            type: "GET",
            dataType: "json",
            success : function (data){
                console.log(data)
                $('#nombreRol').val(data.nombreRol)
            }
        })
    }else {
        $('#nombreRol').val('')
    }

    $('#modal').modal('show')
}