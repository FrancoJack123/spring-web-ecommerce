function abrirModal(codigo){
    $('#usuarioId').val(codigo)
    if(codigo != 0){
        $.ajax({
            url:"/Dashboard/Usuarios/" + codigo,
            type:"GET",
            dataType:"json",
            success:function (data){
                console.log(data)
                $('#nombreUsuario').val(data.nombreUsuario)
                $('#apellidoUsuario').val(data.apellidoUsuario)
                $('#dniUsuario').val(data.dniUsuario)
                $('#telefonoUsuario').val(data.telefonoUsuario)
                $('#correoUsuario').val(data.correoUsuario)
                $('#cargoId').val(data.cargoId.rolId)
                $('#passwordUsuario').val(data.passwordUsuario).removeAttr("required")
            }
        })
        $('#modal').modal('show')
    }else{
        $('#modal').modal('show')
    }

}

function activeModal(codigo){
    $('#usuarioIdActivar').val(codigo)
    $('#staticBackdrop').modal('show')
}

function Limpieza(){
    $('#form').removeClass('was-validated');
    $('#usuarioId').val(0)
    $('#nombreUsuario').val("")
    $('#apellidoUsuario').val("")
    $('#dniUsuario').val("")
    $('#telefonoUsuario').val("")
    $('#correoUsuario').val("")
    $('#passwordUsuario').val("").attr("required",true)
}
