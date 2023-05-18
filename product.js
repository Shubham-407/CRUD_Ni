function fetchProducts() {
    fetch('/product')
      .then(response => response.json())
      .then(data => {
        // Assuming there is a div with the id "productList" to display the data
        const productListDiv = document.getElementById('productList');
        productListDiv.innerHTML = '';
  
        data.forEach(product => {
          const productItem = document.createElement('p');
          productItem.textContent = `Product ID: ${product.productId}, Product Name: ${product.productName}, Category ID: ${product.categoryId}`;
          productListDiv.appendChild(productItem);
        });
      })
      .catch(error => {
        console.error('Error fetching product data:', error);
      });
  }
  
  // Call the functions to fetch and display the data on page load
  document.addEventListener('DOMContentLoaded', function() {
    fetchProducts();
  });
