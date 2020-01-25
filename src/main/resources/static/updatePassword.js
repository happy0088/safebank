
  
  const app = document.getElementById('root');
  function updatePassword(){
    alert("updatePassword calld");
   var password=document.getElementById("password").value;
   //var user=document.getElementById("name").value;
   var cid=getCookie('cid');
    var request = new XMLHttpRequest();    
    request.open('POST', 'http://localhost:8080/api/v1/updatePassword', true);
   // request.setRequestHeader("Content-Type", "application/json");
    request.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
   // request.setRequestHeader('Access-Control-Allow-Origin','http://localhost:8080');
    request.onload = function () {    
      alert("response came");
      // Begin accessing JSON data here
      var data = JSON.parse(this.response);
      alert(data);
      if (request.status >= 200 && request.status < 400) {
        alert("Password updated successfully for : Cid"+data.cid);
      
     const h1 = document.createElement('h1');
      h1.textContent = "Password Updated for "+data.cid;
      app.appendChild(h1);
      } else {
        alert("Error while updating users details");
      }
    } 
    alert(cid);
    // var cid=10001;
    // var password='sSucks';;
    // var obj = { 'cid' : cid, 'password': password     };
    //request.send(JSON.stringify(obj));
    var objString = "cid="+cid+"&password= "+password;
    request.send(objString);
    }

    




