
  
  const app = document.getElementById('root');
  function getfundTransferHistory(){
    var cid=getCookie('cid');
    alert("getfundTransferHistory calld"+cid);
  
  
    var request = new XMLHttpRequest();
    request.open('POST', 'http://localhost:8080/api/v1/getfundTransferHistory', true);
    request.setRequestHeader("Content-Type", "application/json");
    request.setRequestHeader('Access-Control-Allow-Origin','http://localhost:8080');
    request.onload = function () {    
      // Begin accessing JSON data here
      var data = JSON.parse(this.response);
      alert(data);
      if (request.status >= 200 && request.status < 400) {
        alert("login validated: Cid"+data.cid);
       //Create a HTML Table element.
       var table = document.createElement("TABLE");
       table.border = "1";
       //Get the count of columns.
       alert(data.length);
       var columnCount = 4;
       //Add the header row.
       var row = table.insertRow(-1);
      // for (var i = 0; i < columnCount; i++) {
           var headerCell = document.createElement("TH");
           headerCell.innerHTML = 'Customer Id';
           row.appendChild(headerCell);
           var headerCell = document.createElement("TH");
           headerCell.innerHTML = 'Customer Account Id';
           row.appendChild(headerCell);
           var headerCell = document.createElement("TH");
           headerCell.innerHTML = 'Amount';
           row.appendChild(headerCell);
           var headerCell = document.createElement("TH");
           headerCell.innerHTML = 'Payee Account ID';
           row.appendChild(headerCell);
       //}

        data.forEach(userData => {
          row = table.insertRow(-1);
        //  for (var j = 0; j < columnCount; j++) {
              var cell = row.insertCell(-1);
              cell.innerHTML = userData.cid;

              var cell = row.insertCell(-1);
              cell.innerHTML = userData.accountId;

              var cell = row.insertCell(-1);
              cell.innerHTML = userData.amount;

              var cell = row.insertCell(-1);
              cell.innerHTML = userData.payeeAccountId;
          //}


        });

        var dvTable = document.getElementById("transferHistory");
        dvTable.innerHTML = "";
        dvTable.appendChild(table);
        
     const h1 = document.createElement('h1');
      h1.textContent = "Transfer Details for A/C "+cid;
      app.appendChild(h1);
      } else {
        alert("Error while fetching users details");
      }
    }
    //alert(user);
    //var obj = '{      "name" : user      }';
    request.send(cid);
    }

  