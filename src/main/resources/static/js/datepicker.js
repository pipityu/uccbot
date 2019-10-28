function addDate() {

   /* var xmlhttp = new XMLHttpRequest();
    xmlhttp.open("GET", "https://sampleucc.herokuapp.com/info", true);
        xmlhttp.onreadystatechange = function () {
        if (this.readyState == 4 && this.status == 200) {
            var myObj = JSON.parse(this.responseText);
            document.getElementById("leker").innerHTML = myObj.category;
            alert("ide jutott");
        }
    };
    xmlhttp.send();*/

   var resp = fetch('https://sampleucc.herokuapp.com/info');
   var json = resp.json();
    document.getElementById("leker").innerHTML = json.category;


/*        var startDate = $("#startDate").val();
        var endDate =  $("#endDate").val();
        var requestType = $("input[name=radio]:checked", "#dateForm").val();

        if(startDate == "" || endDate == "" || (startDate > endDate) || (typeof requestType == "undefined")) {
            alert("A dátumok vagy a kérelem nem megfelelő, kérlek ellenőrizd!" +
                "");
        }
        else{
                alert(requestType);
                $("#dateForm").attr("action","/feldolgoz");
                alert(startDate);
             //   $("#dateForm").on("submit",function () { alert("okok"); });
        }*/
}