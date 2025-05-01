window.addEventListener("DOMContentLoaded", () => {
    const uploadForm = document.getElementById("uploadForm");
    const imageInput = document.getElementById("image");
    const preview = document.getElementById("preview");
    const resultCard = document.getElementById("resultCard");
    const resultText = document.getElementById("resultText");

    function previewImage(file) {
        if (file) {
            preview.src = URL.createObjectURL(file);
            preview.style.display = "block";
        }
    }

    uploadForm.addEventListener("submit", function (e) {
        e.preventDefault();

        const file = imageInput.files[0];

        if (!file) {
            alert("Bitte ein Bild auswählen.");
            return;
        }

        if (file.size > 10 * 1024 * 1024) {
            alert("Datei zu groß (max. 10MB).");
            return;
        }

        previewImage(file);

        const formData = new FormData();
        formData.append("image", file);

        fetch("/analyze", {
            method: "POST",
            body: formData
        })
        .then(response => response.json())
        .then(data => {
            let formatted = data.map(entry =>
                `${entry.className}: ${(entry.probability * 100).toFixed(2)}%`
            ).join("<br>");
            resultText.innerHTML = formatted;
            resultCard.classList.remove("d-none");
        })
        .catch(error => {
            console.error("Fehler:", error);
            resultText.innerHTML = "Fehler bei der Klassifikation.";
            resultCard.classList.remove("d-none");
        });
    });
});
