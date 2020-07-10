const BASE_URL = "http://localhost:8082";
const updatedname = document.getElementById('updatedname'); 

class Recipe {
    constructor(name, ingredients, time) {
        this.name = name;
        this.ingredients = ingredients;
        this.time = time;
    }
}
/*
UI Class: handles UI tasks
*/
class UI {

    static addRecipeToList(recipe) {
        const list = document.querySelector('#recipe-list');

        const row = document.createElement('tr');

        row.innerHTML = `
            <td>${recipe.name}</td>
            <td>${recipe.ingredients}</td>
            <td>${recipe.time}</td>
            <td><a href="#updateForm" class="btn btn-link"onClick = updateRecipe(${recipe.id})>Edit Name</a></td>
            <td><a href="#" class="btn btn-danger btn-sm delete"onclick = deleteRecipe(${recipe.id})>X</a></td>
        `;

        list.appendChild(row);
    }

    static showAlert(message, className) {
        const div = document.createElement('div');
        div.className = `alert alert-${className}`;
        div.appendChild(document.createTextNode(message));
        const container = document.querySelector('#recipeForm').parentNode;
        const form = document.querySelector('#recipeForm');
        container.insertBefore(div, form);
        //Vanish in 3 seconds
        setTimeout(() => document.querySelector('.alert').remove(), 3000);
    }
    static clearFields() {
        document.querySelector('#name').value = '';
        document.querySelector('#ingredients').value = '';
        document.querySelector('#time').value = '';
    }
}

/* Add A Recipe 
*/

(function () {
    document.getElementById("recipeForm").addEventListener('submit', function (event) {
        event.preventDefault();

        const name = document.querySelector('#name').value;
        const ingredients = document.querySelector('#ingredients').value;
        const time = document.querySelector('#time').value;

        if (name === '' || ingredients === '' || time === '') {
            UI.showAlert('Please fill in all fields', 'danger');
        } else {
            const recipe = new Recipe(name, ingredients, time);

            axios({
                method: 'post',
                url: BASE_URL + '/recipe/create',
                data: {
                    "name": name,
                    "time": time
                }
            }).then(res => {
                axios({
                    method: 'post',
                    url: BASE_URL + '/ingredient/createMulti/' + res.data.id,
                    data: ingredients.split(", ")
                })
            }).then(res => UI.showAlert('Recipe Added', 'success'))
                .then(res => {
                    UI.clearFields()
                    UI.addRecipeToList(recipe)
                    console.log(res)
                })
                .catch(err => console.log(err));
        }
    });
})();

/* Read All Recipes
*/ 
(function () {
    axios.get(BASE_URL + '/recipe/read')
        .then(res => {
         
            res.data.forEach(recipe => {
                recipe.ingredients = recipe.ingredients.map(ingredient => ingredient.name);
                UI.addRecipeToList(recipe);
               
            })
        }).catch(err => console.log(err))

})();

/* Update Recipes
*/
function updateRecipe(id) {
    updatedname.disabled = false;
    updatedname.focus();
    
    document.getElementById("updateForm").addEventListener('submit', function (update) {
        update.preventDefault();
       
        const name = updatedname.value;
        axios({
            method: 'put',
            url: `${BASE_URL}/recipe/update/${id}`,
            data: {
                "name": name
            }
        }).then(res => console.log(res))
        .then(res => location.reload())
        .catch(err => console.log(err));
})
}

/* Delete Recipes
*/
    function deleteRecipe(id) {
        axios.delete(`${BASE_URL}/recipe/delete/${id}`)
            .then(res => console.log(res))
            .then(res => location.reload())
            .catch(err => console.log(err));
    }