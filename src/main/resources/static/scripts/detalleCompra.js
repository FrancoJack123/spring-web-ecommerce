function abrirModal(codigo){
    $.ajax({
        url:"/Ecommerce/listaDetallePedido/" + codigo,
        type:"GET",
        dataType:"json",
        success:function (data){
            const tablaBody = $('#table');
            tablaBody.empty();
            $.each(data, function(index, item) {
                const fila = $('<tr>').appendTo(tablaBody);
                fila.attr("class", "text-center")
                fila.append(`<td><img src="/assets/${item.productoId.foto}" width="80" height="80" class="rounded-4"></td>`);
                fila.append(`<td class="align-middle">${item.productoId.nombreProducto}</td>`);
                fila.append(`<td class="align-middle">${item.productoId.precioProducto}</td>`);
                fila.append(`<td class="align-middle">${item.cantidadDetalle}</td>`);
                fila.append(`<td class="align-middle">${item.precioVentaDetalle}</td>`);
            });
        }
    })

    $('#modal').modal('show')
}