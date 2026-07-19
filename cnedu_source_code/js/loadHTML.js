// HTML 파일 불러오기 함수
function loadHTML(url, targetElementId, callback) {
    fetch(url)
        .then(response => {
            if (!response.ok) {
                throw new Error(`Failed to load ${url}: ${response.statusText}`);
            }
            return response.text();
        })
        .then(data => {
            document.getElementById(targetElementId).innerHTML = data;
            if (callback) callback(); // HTML이 로드된 후 콜백 실행
        })
        .catch(error => {
            console.error(`Error loading HTML file: ${error}`);
        });
}

// HTML 로드
document.addEventListener('DOMContentLoaded', () => {
    loadHTML('/inc/header.html', 'header', setupHeaderScroll); // 헤더 로드 후 스크롤 설정
    loadHTML('/inc/footer.html', 'footer'); // 푸터 로드
    loadHTML('/inc/snb.html', 'snb', setupSnb); // SNB 로드 후 설정
});