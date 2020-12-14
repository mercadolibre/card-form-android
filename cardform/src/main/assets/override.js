var urlAction = recorder.getUrlAction()
var redirectionForm = document.querySelector("form[action='" + urlAction + "']")
if(redirectionForm) {
    var elements = redirectionForm.elements
    var formData = elements[recorder.getKeyData()].value
    recorder.processFormData(formData)
}