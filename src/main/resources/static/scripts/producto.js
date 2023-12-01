function abrirModal(codigo){
    $('#form').removeClass('was-validated')
    $('#productoId').val(codigo)

    if(codigo != 0){
        $.ajax({
            url:"/Dashboard/Productos/" + codigo,
            type:"GET",
            dataType:"json",
            success:function (data){
                $('#nombreProducto').val(data.nombreProducto)
                $('#precioProducto').val(data.precioProducto)
                $('#cantidadProducto').val(data.cantidadProducto)
                $('#descripProducto').val(data.descripProducto)
                $('#descProducto').val(data.descProducto)
                $('#categoriaId').val(data.categoriaId.categoriaId)
                $('#marcaId').val(data.marcaId.marcaId)
                $("#formFile").val("").removeAttr("required")
                $('#iamgen').attr("src",("/assets/" + data.foto))
            }
        })
    }else {
        $('#nombreProducto').val("")
        $('#precioProducto').val("")
        $('#descripProducto').val("")
        $('#descProducto').val("")
        $('#cantidadProducto').val("")
        $("#formFile").val("").attr("required",true);
        $('#iamgen').attr("src","https://dummyimage.com/450x300/dee2e6/6c757d.jpg")
    }

    $('#modal').modal('show')
}

function activeModal(codigo){
    $('#productoActivarId').val(codigo)
    $('#staticBackdrop').modal('show')
}

document.getElementById('formFile').onchange = function (e){
    let reader = new FileReader();
    reader.readAsDataURL(e.target.files[0]);
    reader.onload = function (){
        let preview = document.getElementById('preview');
        let imagen = document.createElement('img');
        imagen.id= "iamgen";
        imagen.src = reader.result;
        imagen.className= "rounded";
        imagen.style.width="380px";
        imagen.style.height="330px";
        preview.innerHTML = '';
        preview.append(imagen);
    }
}