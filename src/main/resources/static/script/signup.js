
    function showUserForm() {
        document.getElementById('userForm').classList.remove('hidden');
        document.getElementById('employerForm').classList.add('hidden');
        document.getElementById('userButton').classList.add('active');
        document.getElementById('employerButton').classList.remove('active');
    }

    function showEmployerForm() {
        document.getElementById('employerForm').classList.remove('hidden');
        document.getElementById('userForm').classList.add('hidden');
        document.getElementById('employerButton').classList.add('active');
        document.getElementById('userButton').classList.remove('active');
    }