document.addEventListener("DOMContentLoaded", function () {
    // Event listener for showing the form
    const links = document.querySelectorAll('nav ul li a');
    links.forEach(link => {
        link.addEventListener('click', function (event) {
            const formId = this.getAttribute('onclick').match(/'([^']+)'/)[1];
            showForm(formId);
        });
    });

    // Event listener for Add Book form submission
    const addBookForm = document.getElementById('addBook');
    if (addBookForm) {
        addBookForm.addEventListener('submit', handleAddBook);
    }

    // Event listener for Borrow Book form submission
    const borrowBookForm = document.getElementById('borrowBook');
    if (borrowBookForm) {
        borrowBookForm.addEventListener('submit', handleBorrowBook);
    }

    // Event listener for Return Book form submission
    const returnBookForm = document.getElementById('returnBook');
    if (returnBookForm) {
        returnBookForm.addEventListener('submit', handleReturnBook);
    }

    // Event listener for Search Book form submission
    const searchBookForm = document.getElementById('searchBook');
    if (searchBookForm) {
        searchBookForm.addEventListener('submit', handleSearchBook);
    }
});

// Function to show the selected form
function showForm(formId) {
    // Hide all forms
    const forms = document.querySelectorAll('.form-container');
    forms.forEach(form => form.style.display = 'none');

    // Show the selected form
    document.getElementById(formId).style.display = 'block';
}

// Function to handle Add Book form submission
function handleAddBook(event) {
    event.preventDefault(); // Prevent the form from submitting the traditional way

    const title = document.getElementById("title").value;
    const author = document.getElementById("author").value;

    // Create a new FormData object
    const formData = new FormData();
    formData.append('title', title);
    formData.append('author', author);

    // Send data to the server via AJAX
    fetch('AddBookServlet', {
        method: 'POST',
        body: formData
    })
    .then(response => response.json())
    .then(data => {
        document.getElementById("addBookResponse").textContent = data.message;
    })
    .catch(error => {
        console.error('Error:', error);
        document.getElementById("addBookResponse").textContent = 'Failed to add the book.';
    });
}

// Function to handle Borrow Book form submission
function handleBorrowBook(event) {
    event.preventDefault(); // Prevent the form from submitting the traditional way

    const bookId = document.getElementById("borrowBookId").value;
    const userId = document.getElementById("userId").value;

    // Create a new FormData object
    const formData = new FormData();
    formData.append('bookId', bookId);
    formData.append('userId', userId);

    // Send data to the server via AJAX
    fetch('BorrowBookServlet', {
        method: 'POST',
        body: formData
    })
    .then(response => response.json())
    .then(data => {
        document.getElementById("borrowBookResponse").textContent = data.message;
    })
    .catch(error => {
        console.error('Error:', error);
        document.getElementById("borrowBookResponse").textContent = 'Failed to borrow the book.';
    });
}

// Function to handle Return Book form submission
function handleReturnBook(event) {
    event.preventDefault(); // Prevent the form from submitting the traditional way

    const bookId = document.getElementById("returnBookId").value;
    const userId = document.getElementById("returnUserId").value;

    // Create a new FormData object
    const formData = new FormData();
    formData.append('bookId', bookId);
    formData.append('userId', userId);

    // Send data to the server via AJAX
    fetch('ReturnBookServlet', {
        method: 'POST',
        body: formData
    })
    .then(response => response.json())
    .then(data => {
        document.getElementById("returnBookResponse").textContent = data.message;
    })
    .catch(error => {
        console.error('Error:', error);
        document.getElementById("returnBookResponse").textContent = 'Failed to return the book.';
    });
}

// Function to handle Search Book form submission
function handleSearchBook(event) {
    event.preventDefault(); // Prevent the form from submitting the traditional way

    const query = document.getElementById("searchQuery").value;

    // Send data to the server via AJAX
    fetch('SearchBookServlet?query=' + encodeURIComponent(query))
    .then(response => response.json())
    .then(data => {
        const resultsDiv = document.getElementById("searchResults");
        resultsDiv.innerHTML = ''; // Clear previous results
        if (data.books && data.books.length > 0) {
            data.books.forEach(book => {
                const bookDiv = document.createElement('div');
                bookDiv.textContent = `Title: ${book.title}, Author: ${book.author}`;
                resultsDiv.appendChild(bookDiv);
            });
        } else {
            resultsDiv.textContent = 'No books found.';
        }
    })
    .catch(error => {
        console.error('Error:', error);
        document.getElementById("searchResults").textContent = 'Failed to search books.';
    });
}
