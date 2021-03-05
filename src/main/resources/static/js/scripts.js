function showImage(event) {
    var output = document.getElementById('showMainImage');
    output.src = URL.createObjectURL(event.target.files[0]);
    output.onload = function() {
        URL.revokeObjectURL(output.src)
    }
}

function add_recipe() {
    let recipe = {
        name: document.getElementById('name').value,
        description: document.getElementById('description').value,
    };
    if (checkData(recipe) && checkImageUploading()) {
        add_recipe_request(recipe);
    }
}

function fork_recipe() {
    let recipe = {
        name: document.getElementById('name').value,
        description: document.getElementById('description').value,
        parentId: document.getElementById('forkedRecipeId').value
    };
    if (checkData(recipe) && checkImageUploading()) {
        add_recipe_request(recipe);
    }
}

function update_recipe() {
    let recipe = {
        name: document.getElementById('name').value,
        description: document.getElementById('description').value,
        id: document.getElementById('recipeId').value,
    };
    if (checkData(recipe)) {
        $.ajax({
            url: "/update_recipe",
            type: "POST",
            contentType: 'application/json',
            dataType: 'json',
            async: false,
            data: JSON.stringify(recipe),
            success: (date) => {
            }
        });
        if (document.getElementById('mainImage').files[0] !== undefined){
            uploadMainImage(recipe.id)
        } else window.location.replace("/recipe?id=" + recipe.id);
    }
}

function add_recipe_request(recipe) {
    $.ajax({
        url: "/add_recipe",
        type: "POST",
        contentType: 'application/json',
        dataType: 'json',
        async: false,
        data: JSON.stringify(recipe),
        success: (date) => {
            uploadMainImage(date)
        }
    });
}

function uploadMainImage(id) {
    let formData = new FormData();
    formData.append('image', document.getElementById('mainImage').files[0]);
    formData.append('id', id);
    fetch('/add_recipe_image', {
        method: 'POST',
        body: formData,
    }).then(data => {
        if (data.status === 200) window.location.replace("/recipe?id=" + id);
    });
}

 function checkData(recipe){
     if (recipe.name === '' || recipe.description === '') {
         alert("Fill all fields!");
         return false;
     }
     return true
 }

function checkImageUploading(){
    if (document.getElementById('mainImage').files[0] === undefined){
        alert("Upload some image!");
        return false;
    }
    return true
}