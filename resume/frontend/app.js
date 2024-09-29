const loginApi = 'http://localhost:8080/v2/users/login'
const logoutApi = 'http://localhost:8080/v2/users/logout'
const getUserByIdApi = 'http://localhost:8080/v2/users'
const getAllUserApi = 'http://localhost:8080/v2/users'
const updateUserApi = 'http://localhost:8080/v2/users'
const changePasswordApi = 'http://localhost:8080/v2/users/password'
let accessToken = ''

document.getElementById("updateBtn").addEventListener("click", function() {
  document.getElementById("navigationBtn").style.display = "none";
  document.getElementById("updateForm").style.display = "block";
})

document.getElementById("updateForm").addEventListener("submit", function(event) {
  event.preventDefault();
  const id = document.getElementById("updateId").value;
  const newUsername = document.getElementById("updateUsername").value;
  let api = `${updateUserApi}/${id}`
  
  if (!accessToken) {
    console.error('No access token available');
    return;
  }

  fetch(api, {
    method: "PUT",
    headers: {
      "content-Type": "application/json",
      'Authorization': `Bearer ${accessToken}`
    },
    body: JSON.stringify({
      username: newUsername
    })
  })
    .then(response => {
      if(!response.ok) {
        console.log(response.status)
        throw new Error('Network response was not ok');
      }
      else {
        if (response.status == 200) {
          alert("User updated!");
          document.getElementById("updateForm").style.display = "none";
          document.getElementById("navigationBtn").style.display = "block";
          return response.json();
        }
      }
    })
});

document.getElementById("changePasswordBtn").addEventListener("click", function () {
  document.getElementById("navigationBtn").style.display = "none";
  document.getElementById("passwordForm").style.display = "block";
});

document.getElementById("passwordForm").addEventListener("submit", function(event) {
  event.preventDefault();
  const id = document.getElementById("passwordId").value;
  const oldPassword = document.getElementById("old-password").value;
  const newPassword = document.getElementById("new-password").value;
  let api = `${changePasswordApi}/${id}`
  
  if (!accessToken) {
    console.error('No access token available');
    return;
  }

  fetch(api, {
    method: "PUT",
    headers: {
      "content-Type": "application/json",
      'Authorization': `Bearer ${accessToken}`
    },
    body: JSON.stringify({
      old_password: oldPassword,
      new_password: newPassword
    })
  })
    .then(response => {
      if(!response.ok) {
        console.log(response.status)
        throw new Error('Network response was not ok');
      }
      else {
        if (response.status == 200) {
          alert("Password updated!");
          document.getElementById("passwordForm").style.display = "none";
          document.getElementById("navigationBtn").style.display = "block";
          return response.json();
        }
      }
    })
});

document.getElementById("loginForm").addEventListener("submit", function (event) {
  event.preventDefault();

  const username = document.getElementById("username").value;
  const password = document.getElementById("password").value;

  fetch(loginApi, {
    method: "POST",
    headers: {
      "content-Type": "application/json",
    },
    body: JSON.stringify({
      username: username,
      password: password
    })
  })
    .then(response => {
      if (!response.ok) {
        console.log(response.status)
        throw new Error('Network response was not ok');
      }
      else {
        if (response.status == 200) {
          alert("Login successful!");
          document.getElementById("loginForm").style.display = "none";
          document.getElementById("navigationBtn").style.display = "block";
          return response.json();
        }
      }
    })
    .then(data => {
      console.log(data);
      accessToken = data.access_token
    })
    .catch(error => {
      console.error('Error:', error);
    });
  });

document.getElementById("logoutBtn").addEventListener("click", function () {
  if (!accessToken) {
    console.error('No access token available for logout');
    return;
  }
  fetch(logoutApi, {
    method: "POST",
    headers: {
      "content-Type": "application/json",
      'Authorization': `Bearer ${accessToken}`
    }
  })
  .then(response => {
    if (!response.ok) {
      console.log(response.status)
      throw new Error('Network response was not ok');
    }
    else {
      if (response.status == 200) {
        alert("Logged out!");
        document.getElementById("loginForm").style.display = "block";
        document.getElementById("navigationBtn").style.display = "none";
        return response.json();
      }
    }
  })
  .then(data => {
    console.log(data);
  })
  .catch(error => {
    console.error('Error:', error);
  });
});

document.getElementById("showOneBtn").addEventListener("click", function () {
  document.getElementById("navigationBtn").style.display = "none";
  document.getElementById("viewForm").style.display = "block";
});

document.getElementById("viewForm").addEventListener("submit", function (){
  event.preventDefault();
  if (!accessToken) {
    console.error('No access token available');
    return;
  }
  const id = document.getElementById("viewId").value;
  let api = `${getUserByIdApi}/${id}`
  fetch(api, {
    method: "GET",
    headers: {
      "content-Type": "application/json",
      'Authorization': `Bearer ${accessToken}`
    }
  })
  .then(response => {
    if (!response.ok) {
      console.log(response.status)
      throw new Error('Network response was not ok');
    }
    return response.json();
  })
  .then(data => {
    console.log(data);
  })
  .catch(error => {
    console.error('Error:', error);
  });
})

document.getElementById("showAllBtn").addEventListener("click", function () {
  if (!accessToken) {
    console.error('No access token available');
    return;
  }
  fetch(getAllUserApi, {
    method: "GET",
    headers: {
      "content-Type": "application/json",
      'Authorization': `Bearer ${accessToken}`
    }
  })
  .then(response => {
    if (!response.ok) {
      console.log(response.status)
      throw new Error('Network response was not ok');
    }
    return response.json();
  })
  .then(data => {
    console.log(data);
  })
  .catch(error => {
    console.error('Error:', error);
  });
});

document.getElementById("viewFormBackBtn").addEventListener("click", function() {
  document.getElementById("viewForm").style.display = "none";
  document.getElementById("navigationBtn").style.display = "block";
})

document.getElementById("passwordFormBackBtn").addEventListener("click", function() {
  document.getElementById("passwordForm").style.display = "none";
  document.getElementById("navigationBtn").style.display = "block";
})

document.getElementById("updateFormBackBtn").addEventListener("click", function() {
  document.getElementById("updateForm").style.display = "none";
  document.getElementById("navigationBtn").style.display = "block";
})