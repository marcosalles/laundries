$(".actions").on("change", function () {
  var id = $(this).attr("data-id");
  var model = $(this).attr("data-model");
  var action = $(this.selectedOptions[0]).val();
  if (action) {
    if (action != "/delete" || confirm("Do you wish to destroy this " + model + "?")) {
      window.location.href = "/admin/" + model + "/" + id + action;
    }
  }
});