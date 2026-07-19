export default {
    
    validatePhoneNumber(phoneNumber) {
        if (phoneNumber == "") return false
        const vietnamesePhoneNumberRegex = /^(\+84|84|0)[1-9]\d{8}$/;
  
        // const vietnamesePhoneNumberRegex = /[1-9]\d{8}$/;
        return vietnamesePhoneNumberRegex.test(phoneNumber);
  
    },
    validateEmail(email) {
        if (email == "") return false
        const emailRegex = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;
        return emailRegex.test(email);
    }
}