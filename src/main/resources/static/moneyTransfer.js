

const app = document.getElementById('root');
var radioButtons = document.getElementById("payeeList");

function transferAmount() {
  alert("transferAmount calld");
  var cid = getCookie('cid');
  var amount = document.getElementById("amount").value;
  var payee = document.getElementById("payee").value;
  var request = new XMLHttpRequest();
alert(payee);
  request.open('POST', 'http://localhost:8080/api/v1/transferFunds', true);
  request.setRequestHeader("Content-Type", "application/json");
  request.setRequestHeader('Access-Control-Allow-Origin', 'http://localhost:8080');
  request.onload = function () {
    // Begin accessing JSON data here
    var data = JSON.parse(this.response);
    alert(data);
    if (request.status >= 200 && request.status < 400) {
      alert("login validated: Cid" + data.cid);
      // <input type="radio" name="favorite_pet" value="Cats">Cats<br>
      // <input type="radio" name="favorite_pet" value="Dogs">Dogs<br>
      // <input type="radio" name="favorite_pet" value="Birds">Birds<br>

      const h1 = document.createElement('h1');
      h1.textContent = amount+" Rs. successfully transferred to "+payee;
      document.getElementById('accountBalance').innerHTML = data.balance;
      app.appendChild(h1);
    } else {
      alert("Error while fetching users details");
    }
  }

  var obj = { 'cid': cid, 'payeeAccountId': payee, 'amount':amount };
  request.send(JSON.stringify(obj));
}








function fetchPayee() {
  alert("fetchPayee calld");
  var cid = getCookie('cid');

  var request = new XMLHttpRequest();

  request.open('POST', 'http://localhost:8080/api/v1/fetchPayeeList', true);
  request.setRequestHeader("Content-Type", "application/json");
  request.setRequestHeader('Access-Control-Allow-Origin', 'http://localhost:8080');
  request.onload = function () {
    // Begin accessing JSON data here
    var data = JSON.parse(this.response);
    alert(data);
    if (request.status >= 200 && request.status < 400) {
      alert("login validated: Cid" + data.cid);

      data.forEach(userData => {
        // var radioHtml='<input type="radio" id="' + userData.payeeAccountId + '" name="' + data.payeeAccountId + '" />';
        // radioInput = document.createElement(radioHtml);
        // radioButtons.append(radioInput);

        var labelno = document.createElement("label");
        labelno.for = userData.payeeAccountId;
        labelno.innerHTML = userData.payeeName;
        radioButtons.appendChild(labelno);

        const inputyes = document.createElement('input');
        inputyes.type = "radio";
        inputyes.id = "payee";
        inputyes.name="payee";
        inputyes.value = userData.payeeAccountId;
        radioButtons.appendChild(inputyes);

      });

    } else {
      alert("Error while fetching users details");
    }
  }

  request.send(cid);
}



