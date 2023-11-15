function abrirModal(codigo){
    $('#form').removeClass('was-validated');

    $('#marcaId').val(codigo)

    if(codigo != 0){
        $.ajax({
            url:"/Dashboard/Marcas/" + codigo,
            type:"GET",
            dataType:"json",
            success:function (data){
                console.log(data)
                $('#nombreMarca').val(data.nombreMarca)
            }
        })
    }else{
        $('#nombreMarca').val("")
    }

    $('#modal').modal('show')
}