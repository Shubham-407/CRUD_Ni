function fetchCategories() {
    fetch('/category')
      .then(response => response.json())
      .then(data => {
        // Assuming there is a div with the id "categoryList" to display the data
        const categoryListDiv = document.getElementById('categoryList');
        categoryListDiv.innerHTML = '';
  
        data.forEach(category => {
          const categoryItem = document.createElement('p');
          categoryItem.textContent = `Category ID: ${category.categoryId}, Category Name: ${category.categoryName}`;
          categoryListDiv.appendChild(categoryItem);
        });
      })
      .catch(error => {
        console.error('Error fetching category data:', error);
      });
  }
  
  // Call the functions to fetch and display the data on page load
  document.addEventListener('DOMContentLoaded', function() {
    fetchCategories();
  });


