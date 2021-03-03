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
    add_recipe_request(recipe);
}

function fork_recipe() {
    let recipe = {
        name: document.getElementById('name').value,
        description: document.getElementById('description').value,
        parentId: document.getElementById('forkedRecipeId').value
    };
    add_recipe_request(recipe);
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
            console.log(date);
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
        if (data.status === 200) window.location.replace("/");
    });
}