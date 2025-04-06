// Функция для переключения в режим редактирования
    function editResume(event, resumeId) {
        event.preventDefault(); // Отменяем стандартное поведение ссылки

        const card = document.getElementById(`resume-${resumeId}`);
        const staticView = card.querySelector('.static-view');
        const editableView = card.querySelector('.editable');

        // Переключаем видимость
        staticView.style.display = 'none';
        editableView.style.display = 'block';
    }

    function saveResume(event, resumeId) {
        event.preventDefault(); // Отменяем стандартное поведение кнопки

        const card = document.getElementById(`resume-${resumeId}`);
        const position = document.getElementById(`position-${resumeId}`).value;
        const description = document.getElementById(`description-${resumeId}`).value;
        const workExperience = document.getElementById(`workExperience-${resumeId}`).value;
        const fileInput = document.getElementById(`file-${resumeId}`);
        const file = fileInput.files[0];

        // Создаем объект FormData
        const formData = new FormData();
        formData.append('id', resumeId);
        formData.append('position', position);
        formData.append('description', description);
        formData.append('workExperience', workExperience);
        if (file) {
            formData.append('file', file);
        }

        // Отправляем данные на сервер
        fetch(`/resume/edit`, {
            method: 'POST',
            body: formData // Отправляем FormData
        })
        .then(response => {
            if (response.ok) {
                const staticView = card.querySelector('.static-view');
                const editableView = card.querySelector('.editable');

                staticView.querySelector('.card-title').textContent = position;
                staticView.querySelector('.card-text:nth-of-type(1)').textContent = description;
                staticView.querySelector('.card-text:nth-of-type(2) span').textContent = workExperience + ' лет';

                // Обновляем имя файла, если файл был загружен
                if (file) {
                    staticView.querySelector('.card-text:nth-of-type(3) a').textContent = file.name;
                }

                staticView.style.display = 'block';
                editableView.style.display = 'none';
            } else {
                console.error('Ошибка при сохранении');
            }
        })
        .catch(error => console.error('Ошибка:', error));
    }