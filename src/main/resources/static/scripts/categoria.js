function abrirModal(codigo){
    $('#form').removeClass("was-validated")
    $('#categoriaId').val(codigo)

    if(codigo != 0){
        $.ajax({
            url:"/Dashboard/Categoria/" + codigo,
            type:"GET",
            dataType:"json",
            success:function (data){
                console.log(data)
                $('#nombreCategoria').val(data.nombreCategoria)
            }
        })
    }else {
        $('#nombreCategoria').val("")
    }

    $('#modal').modal('show')
}