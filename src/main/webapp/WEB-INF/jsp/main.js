let testName = "Простое имя!";
function openForm(obj) {
    console.log("In openForm");
    console.log(obj);
    document.getElementById("tpict").src = "/img/"+obj;
    document.getElementById("tname").value = name;
    document.getElementById("tabout").value = "about";
    document.getElementById("tmanufacturer").value = "manufacturer";
    document.getElementById("tprice").value = "price";
    document.getElementById("tdate").value = "date";
    document.getElementById("tcategory").value = "category";
    document.getElementById("myForm").style.display = "block";
    }
    function closeForm() {
    console.log("In closeForm");
      document.getElementById("myForm").style.display = "none";
    }
    function testClick() {
        alert("In testClick");

        }