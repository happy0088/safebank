
  
  const app = document.getElementById('root');
  function addPayee(){
    alert("addPayee calld");
    var cid=getCookie('cid');
   var account=document.getElementById("account").value;
   var payeeName=document.getElementById("payeeName").value;
    var request = new XMLHttpRequest();
    
    request.open('POST', 'http://localhost:8080/api/v1/addPayee', true);
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
     h1.id="infoBanner";
      h1.textContent = "Benificiary with A/C: "+account +" and Name: "+payeeName +" will be added within 24 hours";
      app.appendChild(h1);
      } else {
        alert("Error while fetching users details");
      }
    }
    
    var obj ={'cid' : cid, 'payeeAccountId': account,'payeeName':payeeName };
    request.send(JSON.stringify(obj));
    }

    



