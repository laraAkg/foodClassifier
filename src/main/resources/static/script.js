document.addEventListener("DOMContentLoaded", () => {
    const MAX_FILE_SIZE = 10 * 1024 * 1024; // 10 MB

    // DOM-Elemente
    const uploadForm = document.getElementById("uploadForm");
    const imageInput = document.getElementById("image");
    const previewImg = document.getElementById("preview");
    const resultCard = document.getElementById("resultCard");
    const resultText = document.getElementById("resultText");
    const labelsList = document.getElementById("labels-list");
    const modelsList = document.getElementById("models-list"); // neu

    // Anzeigefunktionen
    const previewImage = file => {
        previewImg.src = URL.createObjectURL(file);
        previewImg.style.display = "block";
    };
    const showModels = models => {
        modelsList.innerHTML = models
            .map(name => `<li class="modelname">${name}</li>`)
            .join("");
    };

    const showResult = entries => {
        resultText.innerHTML = entries
            .map(e => `${e.className}: ${(e.probability * 100).toFixed(2)}%`)
            .join("<br>");
        resultCard.classList.remove("d-none");
    };

    const showLabels = labels => {
        labelsList.innerHTML = labels
            .map(({ className, description }) =>
                `<li>
             <span class="classname">${className}</span>
             ${description ? `<span class="description">(${description})</span>` : ""}
           </li>`
            )
            .join("");
    };

    const showError = message => {
        resultText.textContent = message;
        resultCard.classList.remove("d-none");
    };

    // Daten-Fetcher
    const fetchLabels = async () => {
        try {
            const resp = await fetch("/labels");
            if (!resp.ok) throw new Error(`Status ${resp.status}`);
            const labels = await resp.json();
            showLabels(labels);
        } catch (err) {
            labelsList.innerHTML =
                `<li style="color:red">Fehler beim Laden: ${err.message}</li>`;
            console.error(err);
        }
    };

    // **Neu:** Fetcher für Models
    const fetchModels = async () => {
        try {
            const resp = await fetch("/models");
            if (!resp.ok) throw new Error(`Status ${resp.status}`);
            const models = await resp.json();
            showModels(models);
        } catch (err) {
            modelsList.innerHTML =
                `<li style="color:red">Fehler beim Laden der Modelle: ${err.message}</li>`;
            console.error(err);
        }
    };

    const handleUpload = async e => {
        e.preventDefault();
        const file = imageInput.files[0];

        if (!file) {
            alert("Bitte ein Bild auswählen.");
            return;
        }
        if (file.size > MAX_FILE_SIZE) {
            alert("Datei zu groß (max. 10 MB).");
            return;
        }

        previewImage(file);

        const formData = new FormData();
        formData.append("image", file);

        try {
            const resp = await fetch("/analyze", { method: "POST", body: formData });
            if (!resp.ok) throw new Error(`Status ${resp.status}`);
            const data = await resp.json();
            showResult(data);
        } catch (err) {
            console.error("Analyse-Fehler:", err);
            showError("Fehler bei der Klassifikation.");
        }
    };

    // Event-Binding & Initial-Load
    uploadForm.addEventListener("submit", handleUpload);
    fetchLabels();
    fetchModels();
});
