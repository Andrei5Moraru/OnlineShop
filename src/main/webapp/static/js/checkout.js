// For next sprint --------------------------

function openModal() {
    document.getElementById("backdrop").style.display = "block"
    document.getElementById("modal").style.display = "block"
    document.getElementById("modal").className += "show"
}
function closeModal() {
    document.getElementById("backdrop").style.display = "none"
    document.getElementById("modal").style.display = "none"
    document.getElementById("modal").className += document.getElementById("modal").className.replace("show", "")
}
// Get the modal
var modal = document.getElementById('modal');

// When the user clicks anywhere outside of the modal, close it
window.onclick = function (event) {
    if (event.target == modal) {
        closeModal()
    }
}
// End of content For next sprint ---------

window.addEventListener('load', function () {
    // Fetch all the forms we want to apply custom Bootstrap validation styles to
    let forms = document.getElementsByClassName('needs-validation');


    // Loop over them and prevent submission
    let validation = Array.prototype.filter.call(forms, function (form) {
        form.addEventListener('submit', function (event) {
            if (form.checkValidity() === false) {
                event.preventDefault();
                event.stopPropagation();
            } //else openModal();

            form.classList.add('was-validated');

        }, false);
    });
}, false);