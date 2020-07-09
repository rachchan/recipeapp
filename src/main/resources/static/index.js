/*
Recipe Class: Represents a recipe
*/
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
    static displayRecipes() {
        axios({
            method: 'get',
            url: 'http://localhost:8082/recipe/read',
            }).then(res => {
                res.data.forEach(recipe => UI.addRecipeToList(recipe));
    })}

    static addRecipeToList(recipe) {
        const list = document.querySelector('#recipe-list');

        const row = document.createElement('tr');

        row.innerHTML = `
            <td>${recipe.name}</td>
            <td>${recipe.ingredients}</td>
            <td>${recipe.time}</td>
            <td><a href="#" class="btn btn-danger btn-sm delete">X</a></td>
        `;

        list.appendChild(row);
    }

    static deleteRecipe(el) {
        if (el.classList.contains('delete')) {
            el.parentElement.parentElement.remove();
        }
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


/*
Event: Display Recipes
*/
document.addEventListener('DOMContentLoaded', UI.displayRecipes);

/*
Event: Add a Recipe
*/
document.querySelector('#recipeForm').addEventListener('submit', (e) => {
    //prevent actual submit
    e.preventDefault();

    //Get form values
    const name = document.querySelector('#name').value;
    const ingredients = document.querySelector('#ingredients').value;
    const time = document.querySelector('#time').value;

    //Validate
    if (name === '' || ingredients === '' || time === '') {
        UI.showAlert('Please fill in all fields', 'danger');
    } else {
        //Instantiate Recipe
        const recipe = new Recipe(name, ingredients, time);

        //Add Recipe to UI
        UI.addRecipeToList(recipe);

        //Show success message
        UI.showAlert('Recipe Added', 'success');

        //Clear fields
        UI.clearFields();

        //send to api
        axios({
            method: 'post',
            url: 'http://localhost:8082/recipe/createRecipe',
            data: {
                "name": name,
                "time": time
            }
        }).then(res => {
            axios({
                method: 'post',
                url: 'http://localhost:8082/ingredient/createMulti/' + res.data.id,
                data: ingredients.split(", ")
            })
        }).then(console.log(res)).catch(err => console.log(err));
    } 


/*
Event: Remove a Recipe
*/
document.querySelector('#recipe-list').addEventListener('click', (e) => {

    //Remove recipe from UI
    UI.deleteRecipe(e.target)

    //show success message
    UI.showAlert('Recipe Removed', 'success');

    //send to api
    axios({
        method: 'delete',
        url: 'http://localhost:8082/recipe/delete/' + res.data.id,
        headers: { 'Content-Type': 'application/json' },
    }).then(function (response) {
        console.log(response);
    }).catch(function(response) {
        console.log(response);
    });
})})