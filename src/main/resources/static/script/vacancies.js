    // Функция для открытия модального окна
    function openApplicationModal(button) {
    const vacancyId = button.getAttribute('data-vacancy-id');
    const vacancyTitle = button.getAttribute('data-vacancy-position');
    const employerName = button.getAttribute('data-employer-organisation');

    const modal = document.getElementById('applicationModal');
    modal.style.display = 'block';

    // Заполняем информацию о работодателе и вакансии
    document.getElementById('employerName').textContent = employerName;
    document.getElementById('vacancyTitle').textContent = vacancyTitle;

    // Загружаем резюме пользователя
    fetch('/application/resume/my')
        .then(response => response.json())
        .then(data => {
            console.log("Данные от сервера:", data); // Выводим данные в консоль
            if (!Array.isArray(data)) {
                console.error("Ожидался массив, но получено:", data);
                return;
            }
            const resumesContainer = document.getElementById('resumes');
            resumesContainer.innerHTML = ''; // Очищаем контейнер
            data.forEach(resume => {
                resumesContainer.innerHTML += `
                    <div>
                        <input type="radio" name="resume" value="${resume.ID}">
                        <label><b>${resume.position}</b></label>
                        <label>${resume.description}</label>
                    </div>
                `;
            });
        })
        .catch(error => {
            console.error("Ошибка при получении данных:", error); // Ловим ошибки
        });

    // Сохраняем ID вакансии в скрытом поле
    document.getElementById('vacancyId').value = vacancyId;
}

    // Функция для закрытия модального окна
    function closeApplicationModal() {
        const modal = document.getElementById('applicationModal');
        modal.style.display = 'none';
    }

    // Функция для отправки заявки
    function sendApplication() {
        const vacancyId = document.getElementById('vacancyId').value;
        const message = document.getElementById('message').value;
        const selectedResume = document.querySelector('input[name="resume"]:checked');

        if (!selectedResume) {
            alert('Пожалуйста, выберите резюме.');
            return;
        }

        const resumeId = selectedResume.value;

        // Отправляем данные на сервер
        fetch('/application/send', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({
                vacancyId: vacancyId,
                resumeId: resumeId,
                message: message
            }),
        })
        .then(response => {
            if (response.ok) {
                alert('Заявка успешно отправлена!');
                closeApplicationModal();
            } else {
                alert('Ошибка при отправке заявки.');
            }
        });
    }