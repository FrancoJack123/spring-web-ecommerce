let table = new DataTable('#datatablesSimple', {
    pageLength: 7,
    lengthChange: false,
    
});

var wrapperDiv = $("#datatablesSimple_wrapper");

// Obtiene los tres divs dentro del contenedor principal
var div1 = wrapperDiv.find(".row:eq(0)");
var div2 = wrapperDiv.find(".row:eq(1)");
var div3 = wrapperDiv.find(".row:eq(2)");

// Agrega IDs a los tres divs
div1.attr("id", "div1");
div2.attr("id", "div2");
div3.attr("id", "div3");

/* Reemplazamos el diseño del buscador */
const datatablesSimpleFilter = document.querySelector('#div1');

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