let table = new DataTable('#datatablesSimple', {
    pageLength: 7,
    lengthChange: false
});

/* Agregamos un div para agregar el boton de agregar */
var divDataTAable =  document.querySelector('.col-sm-12');
var divCrado = document.createElement('div');
divCrado.setAttribute('class', 'dataTables_length');
divCrado.setAttribute('id', 'datatablesSimple_length');

divDataTAable.appendChild(divCrado);

/* Creamos el boton agregar y le ponemos en el div creado */
var rowContainer = document.querySelector('.dataTables_length');

var customButton = document.createElement('button');
customButton.innerHTML = 'Agregar';
customButton.setAttribute('type', 'button');
customButton.setAttribute('class', 'btn mb-2');
customButton.setAttribute('style', 'background-color: #e9ecef; border: 1px solid #ced4da;');
customButton.setAttribute('onclick', 'abrirModal(0)');

rowContainer.appendChild(customButton);

/* Reemplazamos el diseño del buscador */
const datatablesSimpleFilter = document.getElementById('datatablesSimple_filter');

const customSearch = `
<div class="input-group mb-3">
    <span class="input-group-text" id="basic-addon1">Search</span>
    <input type="text" class="form-control" id="custom-search-input">
    <span class="input-group-text" id="basic-addon1"><i class="fas fa-search" style="color: #6c757d;"></i></span>
</div>
`;

/* Asiganamos la funcion de buscar al diseño del buscador modificado */
datatablesSimpleFilter.innerHTML = customSearch;

const customSearchInput = document.getElementById('custom-search-input');

customSearchInput.addEventListener('keyup', function () {
    const table = $('#datatablesSimple').DataTable();

    const searchValue = this.value;

    table.search(searchValue).draw();
});