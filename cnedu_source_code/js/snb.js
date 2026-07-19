function setupSnb() {
    const menuButtons = document.querySelectorAll('.snb_menu_depth2, .snb_menu_depth3, .snb_menu_depth4');
    let activeButton = null; // 현재 활성화된 버튼을 추적

    menuButtons.forEach(button => {
        const anchor = button.querySelector('a');
        const submenu = button.querySelector('.snb_menu_depth3, .snb_menu_depth4');

        button.addEventListener('click', function (event) {
            event.stopPropagation();

            // 클릭한 버튼이 이미 활성화 상태인 경우 토글
            if (button === activeButton) {
                toggleButtonState(button, anchor, submenu);
                activeButton = button.classList.contains('active') ? button : null;
            } else {
                // 다른 버튼을 클릭한 경우, 이전 활성 버튼은 그대로 두고 새로운 버튼만 활성화
                if (activeButton && activeButton !== button) {
                    // 이전 활성 버튼의 상태는 변경하지 않음
                }
                toggleButtonState(button, anchor, submenu);
                activeButton = button.classList.contains('active') ? button : activeButton;
            }
        });

        // 포커스 이벤트 처리
        button.addEventListener('focusin', function () {
            if (anchor && !button.classList.contains('active')) {
                anchor.setAttribute('aria-expanded', 'true');
                if (submenu) submenu.style.display = 'block';
            }
        });

        button.addEventListener('focusout', function (event) {
            setTimeout(() => {
                if (!button.contains(document.activeElement) && !button.classList.contains('active')) {
                    if (anchor) {
                        anchor.setAttribute('aria-expanded', 'false');
                    }
                    if (submenu) submenu.style.display = 'none';
                }
            }, 0);
        });
    });
}

function toggleButtonState(button, anchor, submenu) {
    const isActive = button.classList.toggle('active');
    if (anchor) {
        anchor.setAttribute('aria-expanded', isActive ? 'true' : 'false');
    }
    if (submenu) {
        submenu.style.display = isActive ? 'block' : 'none';
    }
}
