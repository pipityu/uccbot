function addDate() {
/*
    var xmlhttp = new XMLHttpRequest();
    xmlhttp.open("GET", "https://app.botsociety.io/apisociety/2.0/conversations/5db08a3e3c5eab5f7d58da88/messages/5da6dce86419f7722d038f42", true);
    xmlhttp.setRequestHeader("user_id", "5da03a5496750f488b33afd2");
    xmlhttp.setRequestHeader("api_key_public", "8af622394a4c631dffb38a7bc168d269");
    xmlhttp.setRequestHeader("Content-Type", "application/json");
    xmlhttp.onload = function () {
        if (this.readyState == 4 && this.status == 200) {
            var myObj = JSON.parse(this.responseText);
            document.getElementById("leker").innerHTML = myObj.buttons.text;
        }
    };
    xmlhttp.send();
 */

        var startDate = $("#startDate").val();
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
        }
}