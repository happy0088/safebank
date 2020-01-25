
function getUsers() {
  alert("fetch user calld");
  var request = new XMLHttpRequest();
  request.open('GET', 'http://localhost:8080/api/v1/users', true);
  request.onload = function () {

    // Begin accessing JSON data here
    var data = JSON.parse(this.response);
    alert(data);
    if (request.status >= 200 && request.status < 400) {
      data.forEach(userData => {
        const card = document.createElement('div');
        card.setAttribute('class', 'card');
        alert(userData);
        const h1 = document.createElement('h1');
        h1.textContent = userData;
      }

      );
    } else {
      alert("Error while fetching users details");
    }
  }

  request.send();
}


function saveUser() {
  alert("save user calld");

  var userName = document.getElementById("newUserName").value;
  var fullName = document.getElementById("fullName").value;
  var email = document.getElementById("email").value;
  var password = document.getElementById("psw").value;

//alert('{      "userName" : userName, "fullName" : fullName ,"email" : email ,"password" : password  }');
  var request = new XMLHttpRequest();
  request.open('POST', 'http://localhost:8080/api/v1/saveUser', true);
  request.setRequestHeader("Content-Type", "application/json");
  request.setRequestHeader('Access-Control-Allow-Origin', 'http://localhost:8080');
  request.onload = function () {
    // Begin accessing JSON data here
    alert("response recieved");
    var data = JSON.parse(this.response);
    alert(data);
    if (request.status >= 200 && request.status < 400) {
     alert(data);
    } else {
      alert("Error while saving user");
    }
  }
  var obj = { 'userName' : userName , 'fullName' :fullName ,'email' :email ,'password' : password };
  //var obj={'userName':user}; 
  alert(obj);
  request.send(JSON.stringify(obj));
}



function validateLogin() {
  alert("validate login calld");
  var user = document.getElementById("userName").value;
  var request = new XMLHttpRequest();
  request.open('POST', 'http://localhost:8080/api/v1/validate', true);
  request.setRequestHeader("Content-Type", "application/json");
  request.setRequestHeader('Access-Control-Allow-Origin', 'http://localhost:8080');
  request.onload = function () {
    // Begin accessing JSON data here
    var data = JSON.parse(this.response);
    alert(data);
    if (request.status >= 200 && request.status < 400) {
      alert("login validated: Cid" + data.cid);
      // alert("setting loggedInUser=",user);
      document.cookie = " loggedInUser=" + user + "; expires = Thu, 01 Jan 2050 00:00:00 GMT;path=/";
      document.cookie = " cid=" + data.cid + "; expires = Thu, 01 Jan 2050 00:00:00 GMT;path=/";
      //document.cookie = "loggedInUser="+user+";";
      localStorage.setItem("loggedInUser", user);
      localStorage.setItem("balance", data.balance);
      window.location = "Homepage.html";
    } else {
      alert("Error while fetching users details");
    }
  }
  alert(user);
 // var obj = '{      "name" :'+ user+'      }';
  var obj={'userName':user}; 
  request.send(JSON.stringify(obj));
}



function getAccountDetails() {
  alert("getAccountDetails calld");

  var cid = getCookie('cid');
  var request = new XMLHttpRequest();
  request.open('POST', 'http://localhost:8080/api/v1/getAccountDetails', true);
  request.setRequestHeader("Content-Type", "application/json");
  request.setRequestHeader('Access-Control-Allow-Origin', 'http://localhost:8080');
  request.onload = function () {
    // Begin accessing JSON data here
    var data = JSON.parse(this.response);
    alert(data);
    if (request.status >= 200 && request.status < 400) {
      alert("Account details found for : Cid" + data.cid);
     
  document.getElementById('cid').innerHTML = data.cid;
  document.getElementById('balance').innerHTML = data.balance;
  document.getElementById('accountId').innerHTML = data.accountId;
  document.getElementById('accountBalance').innerHTML = data.balance;
  
    } else {
      alert("Error while fetching users Account Summary ");
    }
  }
  alert(cid);
 // var obj = '{      "name" :'+ user+'      }';
  //var obj={'userName':user}; 
  request.send(cid);




  document.getElementById('accountBalance').innerHTML = localStorage.getItem("balance");

}






