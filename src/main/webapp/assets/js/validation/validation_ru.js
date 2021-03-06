function CustomValidation(input) {
    this.invalidities = [];
    this.validityChecks = [];
    this.inputNode = input;
    this.registerListener();
}

CustomValidation.prototype = {
    addInvalidity: function(message) {
        this.invalidities.push(message);
    },

    getInvalidities: function() {
        return this.invalidities.join('. \n');
    },

    checkValidity: function(input) {
        for ( var i = 0; i < this.validityChecks.length; i++ ) {
            var isInvalid = this.validityChecks[i].isInvalid(input);
            if (isInvalid) {
                this.addInvalidity(this.validityChecks[i].invalidityMessage);
            }

            var requirementElement = this.validityChecks[i].element;
            if (requirementElement) {
                if (isInvalid) {
                    requirementElement.classList.add('invalid');
                    requirementElement.classList.remove('valid');
                } else {
                    requirementElement.classList.remove('invalid');
                    requirementElement.classList.add('valid');
                }
            }
        }
    },

    checkInput: function() {

        this.inputNode.CustomValidation.invalidities = [];
        this.checkValidity(this.inputNode);

        if ( this.inputNode.CustomValidation.invalidities.length === 0 && this.inputNode.value !== '' ) {
            this.inputNode.setCustomValidity('');
        } else {
            var message = this.inputNode.CustomValidation.getInvalidities();
            this.inputNode.setCustomValidity(message);
        }
    },

    registerListener: function() {
        var CustomValidation = this;
        this.inputNode.addEventListener('keyup', function() {
            CustomValidation.checkInput();
        });
    }

};

var usernameValidityChecks = [
    {
        isInvalid: function(input) {
            return input.value.length < 5 | input.value.length > 20;
        },
        invalidityMessage: 'Это поле должно быть от 5 до 20 символов в длину',
        element: document.querySelector('label[for="username"] .input-requirements li:nth-child(1)')
    },
    {
        isInvalid: function(input) {
            var illegalCharacters = input.value.match(/^.*[^\w\d._].*$/g);
            return !!illegalCharacters;
        },
        invalidityMessage: 'Допускаются только латиница, цифры, "."" и "_"',
        element: document.querySelector('label[for="username"] .input-requirements li:nth-child(2)')
    }
];

var nameValidityChecks = [
    {
        isInvalid: function(input) {
            return input.value.length < 2 | input.value.length > 50;
        },
        invalidityMessage: 'Это поле должно быть от 2 до 50 символов в длину',
        element: document.querySelector('label[for="name"] .input-requirements li:nth-child(1)')
    },
    {
        isInvalid: function(input) {
            return !input.value.match(/^[A-ZА-Я]/g);
        },
        invalidityMessage: 'Первый символ должен быть заглавной буквой',
        element: document.querySelector('label[for="name"] .input-requirements li:nth-child(3)')
    },
    {
        isInvalid: function(input) {
            var illegalCharacters = input.value.match(/^.*[^a-zа-я].*$/ig);
            return !!illegalCharacters;
        },
        invalidityMessage: 'Допускаются только буквы',
        element: document.querySelector('label[for="name"] .input-requirements li:nth-child(2)')
    }
];

var lastNameValidityChecks = [
    {
        isInvalid: function(input) {
            return input.value.length < 2 && input.value.length > 0 || input.value.length > 50;
        },
        invalidityMessage: 'Это поле должно быть от 2 до 50 символов в длину',
        element: document.querySelector('label[for="last-name"] .input-requirements li:nth-child(1)')
    },
    {
        isInvalid: function(input) {
            return !input.value.match(/^[A-ZА-Я]/g) && input.value.length > 0;
        },
        invalidityMessage: 'Первый символ должен быть заглавной буквой',
        element: document.querySelector('label[for="last-name"] .input-requirements li:nth-child(3)')
    },
    {
        isInvalid: function(input) {
            var illegalCharacters = input.value.match(/^.*[^a-zа-я].*$/ig);
            return illegalCharacters & input != null;
        },
        invalidityMessage: 'Допускаются только буквы',
        element: document.querySelector('label[for="last-name"] .input-requirements li:nth-child(2)')
    }
];

var surnameValidityChecks = [
    {
        isInvalid: function(input) {
            return input.value.length < 2 | input.value.length > 50;
        },
        invalidityMessage: 'Это поле должно быть от 2 до 50 символов в длину',
        element: document.querySelector('label[for="surname"] .input-requirements li:nth-child(1)')
    },
    {
        isInvalid: function(input) {
            return !input.value.match(/^[A-ZА-Я]/g);
        },
        invalidityMessage: 'Первый символ должен быть заглавной буквой',
        element: document.querySelector('label[for="surname"] .input-requirements li:nth-child(3)')
    },
    {
        isInvalid: function(input) {
            var illegalCharacters = input.value.match(/^.*[^a-zа-я].*$/ig);
            return !!illegalCharacters;
        },
        invalidityMessage: 'Допускаются только буквы',
        element: document.querySelector('label[for="surname"] .input-requirements li:nth-child(2)')
    }
];

var emailValidityChecks = [
    {
        isInvalid: function(input) {
            return !input.value.match(/^.*@.*$/g);
        },
        invalidityMessage: 'Поле должно содержать "@"',
        element: document.querySelector('label[for="email"] .input-requirements li:nth-child(2)')
    },
    {
        isInvalid: function(input) {
            var illegalCharacters = input.value.match(/^[A-Za-z._\d\-]+@[A-Za-z]+\.[A-Za-z]{2,3}$/g);
            return !illegalCharacters;
        },
        invalidityMessage: 'Допускаются только латиница, цифры, "_", "-" и "."',
        element: document.querySelector('label[for="email"] .input-requirements li:nth-child(1)')
    }
];

var passwordValidityChecks = [
    {
        isInvalid: function(input) {
            return input.value.length < 5 | input.value.length > 12;
        },
        invalidityMessage: 'Это поле должно быть от 5 до 12 символов в длину',
        element: document.querySelector('label[for="password"] .input-requirements li:nth-child(1)')
    },

    {
        isInvalid: function(input) {
            return !input.value.match(/^[\w\d._]{5,12}$/g);
        },
        invalidityMessage: 'Допускаются только латиница, цифры, "."" и "_"',
        element: document.querySelector('label[for="password"] .input-requirements li:nth-child(2)')
    }
];

var passwordRepeatValidityChecks = [
    {
        isInvalid: function() {
            return passwordRepeatInput.value !== passwordInput.value;
        },
        invalidityMessage: 'Это поле должно совпадать с предыдущим'
    }
];

var usernameInput = document.getElementById('username');
if (usernameInput != null) {
    usernameInput.CustomValidation = new CustomValidation(usernameInput);
    usernameInput.CustomValidation.validityChecks = usernameValidityChecks;
}

var nameInput = document.getElementById('name');
if (nameInput != null) {
    nameInput.CustomValidation = new CustomValidation(nameInput);
    nameInput.CustomValidation.validityChecks = nameValidityChecks;
}

var lastNameInput = document.getElementById('last-name');
if (lastNameInput != null) {
    lastNameInput.CustomValidation = new CustomValidation(lastNameInput);
    lastNameInput.CustomValidation.validityChecks = lastNameValidityChecks;
}

var surnameInput = document.getElementById('surname');
if (surnameInput != null) {
    surnameInput.CustomValidation = new CustomValidation(surnameInput);
    surnameInput.CustomValidation.validityChecks = surnameValidityChecks;
}

var emailInput = document.getElementById('email');
if (emailInput != null) {
    emailInput.CustomValidation = new CustomValidation(emailInput);
    emailInput.CustomValidation.validityChecks = emailValidityChecks;
}

var passwordInput = document.getElementById('password');
if (passwordInput != null) {
    passwordInput.CustomValidation = new CustomValidation(passwordInput);
    passwordInput.CustomValidation.validityChecks = passwordValidityChecks;
}

var passwordRepeatInput = document.getElementById('password-repeat');
if (passwordRepeatInput != null) {
    passwordRepeatInput.CustomValidation = new CustomValidation(passwordRepeatInput);
    passwordRepeatInput.CustomValidation.validityChecks = passwordRepeatValidityChecks;
}

var newPasswordInput = document.getElementById('new-password');
if (newPasswordInput != null) {
    newPasswordInput.CustomValidation = new CustomValidation(newPasswordInput);
    newPasswordInput.CustomValidation.validityChecks = passwordValidityChecks;
}

var changePasswordConfirmInput = document.getElementById('change-confirm-password');
if (changePasswordConfirmInput != null) {
    changePasswordConfirmInput.CustomValidation = new CustomValidation(changePasswordConfirmInput);
    changePasswordConfirmInput.CustomValidation.validityChecks = passwordValidityChecks;
}

var deletePasswordConfirmInput = document.getElementById('delete-confirm-password');
if (deletePasswordConfirmInput != null) {
    deletePasswordConfirmInput.CustomValidation = new CustomValidation(deletePasswordConfirmInput);
    deletePasswordConfirmInput.CustomValidation.validityChecks = passwordValidityChecks;
}

var inputs = document.querySelectorAll('input:not([type="submit"])');

var submit = document.querySelector('input[type="submit"');
if (submit != null) {
    submit.addEventListener('click', validate);
}
var registration = document.getElementById('registration');
if (registration != null) {
    registration.addEventListener('submit', validate);
}
var login = document.getElementById('login');
if (login != null) {
    login.addEventListener('submit', validate);
}
var preferences = document.getElementById('preferences');
if (preferences != null) {
    preferences.addEventListener('submit', validate);
}
function validate() {
    for (var i = 0; i < inputs.length; i++) {
        inputs[i].CustomValidation.checkInput();
    }
}