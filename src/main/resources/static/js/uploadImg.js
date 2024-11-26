'use strict';
$(function () {
    let canvas = document.getElementById('previewCanvas');
    const MAX_FILE_SIZE_MB = 1; // 最大サイズ (MB)
    const MAX_FILE_SIZE = MAX_FILE_SIZE_MB * 1024 * 1024;

    $('#image').on('change', function (event) {

        const img = new Image();
        const fr = new FileReader();
        const file = event.target.files[0];

        if (!file) {
            $('#previewCanvas').hide();
            return;
        }

        if (file.size > MAX_FILE_SIZE) {
            $(this).val('');
            alert(`ファイルサイズは最大${MAX_FILE_SIZE_MB}MB までです`);
            return;
        }


        $('#previewCanvas').show();
        fr.onload = function (e) {
            img.onload = function () {
                canvas.width = 70;
                canvas.height = 130;
                let ctx = canvas.getContext('2d');
                ctx.drawImage(img, 0, 0, canvas.width, canvas.height);
            }
            img.src = e.target.result;
        }

        fr.readAsDataURL(file);
    })
});
