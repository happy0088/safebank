
  
  const app = document.getElementById('root');
  function donate(){
    alert("updatePassword calld");
   var amt=document.getElementById("amt").value;
   //var user=document.getElementById("name").value;
   var cid=getCookie("cid");
    var request = new XMLHttpRequest();
    
    request.open('POST', 'http://localhost:8080/api/v1/donate', true);
    request.setRequestHeader("Content-Type", "application/json");
    request.setRequestHeader('Access-Control-Allow-Origin','http://localhost:8080');
    request.onload = function () {    
      // Begin accessing JSON data here
      var data = JSON.parse(this.response);
      alert(data);
      if (request.status >= 200 && request.status < 400) {
        alert("login validated: Cid"+data.cid);
      
   
     // alert("setting loggedInUser=",user);
     //document.cookie = "loggedInUserHappy=; username=; loggedInUser=; expires = Thu, 01 Jan 1970 00:00:00 GMT";
     const h1 = document.createElement('h1');
      h1.textContent = "Donation made for Rs. "+amt +" and your update balance is "+data.balance;
      app.appendChild(h1);
      } else {
        alert("Error while fetching users details");
      }
    }
    alert(cid,amt);
    var obj = { "cid" : cid, "amount": amt};
    request.send(JSON.stringify(obj));
    }

    




