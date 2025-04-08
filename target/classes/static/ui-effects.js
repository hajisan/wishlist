// ========== Scroll-effekt for topbar ==========
window.addEventListener("scroll", () => {
    const topbar = document.querySelector(".topbar");
    if (!topbar) return;

    if (window.scrollY > 20) {
        topbar.classList.add("scrolled");
    } else {
        topbar.classList.remove("scrolled");
    }
});

// ========== Fade-in effekt for info-boxes ved scroll ==========
const observer = new IntersectionObserver((entries) => {
    entries.forEach(entry => {
        if (entry.isIntersecting) {
            entry.target.classList.add('visible');
            observer.unobserve(entry.target); // Kun én gang
        }
    });
}, {
    threshold: 0.1
});

document.querySelectorAll('.info-box').forEach(box => observer.observe(box));

// ========== Smooth scroll ned til inspiration ==========
document.querySelector('.arrow')?.addEventListener('click', function (e) {
    e.preventDefault();

    const target = document.querySelector('#inspiration');
    const start = window.pageYOffset;
    const end = target.getBoundingClientRect().top + start;
    const distance = end - start;
    const duration = 1500;
    let startTime = null;

    function animation(currentTime) {
        if (!startTime) startTime = currentTime;
        const timeElapsed = currentTime - startTime;
        const run = easeInOutQuad(timeElapsed, start, distance, duration);
        window.scrollTo(0, run);
        if (timeElapsed < duration) requestAnimationFrame(animation);
    }

    function easeInOutQuad(t, b, c, d) {
        t /= d / 2;
        return t < 1
            ? c / 2 * t * t + b
            : -c / 2 * (--t * (t - 2) - 1) + b;
    }

    requestAnimationFrame(animation);
});

// ========== Logo-banner med automatisk og pausebar scroll ==========
document.addEventListener('DOMContentLoaded', function () {
    const track = document.getElementById('scroll-track');
    if (!track) return;

    let position = 0;
    let animationFrame;
    let speed = 1.2;
    let targetSpeed = speed;

    function loop() {
        speed += (targetSpeed - speed) * 0.08;
        position -= speed;
        if (position <= -track.scrollWidth / 2) {
            position = 0;
        }

        track.style.transform = `translateX(${position}px)`;
        animationFrame = requestAnimationFrame(loop);
    }

    track.addEventListener('mouseenter', () => targetSpeed = 0);
    track.addEventListener('mouseleave', () => targetSpeed = 1.2);

    loop();
});

// ========== Login og Signup modal ==========
document.addEventListener("DOMContentLoaded", () => {
    const loginModal = document.getElementById("loginModal");
    const signupModal = document.getElementById("signupModal");

    if (loginModal) {
        window.openModal = () => {
            loginModal.style.display = "flex";
            setTimeout(() => loginModal.classList.add("visible"), 10);
        };

        window.closeModal = () => {
            loginModal.classList.remove("visible");
            setTimeout(() => (loginModal.style.display = "none"), 200);
        };

        loginModal.addEventListener("click", (e) => {
            if (e.target === loginModal) closeModal();
        });

        document.addEventListener("keydown", (e) => {
            if (e.key === "Escape") closeModal();
        });

        const errorFlag = document.getElementById("loginErrorFlag");
        if (errorFlag) {
            loginModal.style.display = "flex";
            setTimeout(() => {
                loginModal.classList.add("visible");
                const box = loginModal.querySelector(".modal-box");
                if (box) {
                    box.classList.add("shake");
                    setTimeout(() => box.classList.remove("shake"), 600);
                }
            }, 10);
        }
    }

    if (signupModal) {
        window.openSignup = () => {
            signupModal.style.display = "flex";
            setTimeout(() => signupModal.classList.add("visible"), 10);
        };

        window.closeSignup = () => {
            signupModal.classList.remove("visible");
            setTimeout(() => (signupModal.style.display = "none"), 200);
        };

        signupModal.addEventListener("click", (e) => {
            if (e.target === signupModal) closeSignup();
        });
    }
});

// ========== Profilmodal ==========
document.addEventListener("DOMContentLoaded", () => {
    const profileModal = document.getElementById("profileModal");
    if (!profileModal) return;

    window.openProfileModal = () => {
        profileModal.style.display = "flex";
        setTimeout(() => profileModal.classList.add("visible"), 10);
    };

    window.closeProfileModal = () => {
        profileModal.classList.remove("visible");
        setTimeout(() => profileModal.style.display = "none", 200);
    };

    profileModal.addEventListener("click", (e) => {
        if (e.target === profileModal) closeProfileModal();
    });
});

// ========== Edit profilmodal ==========
document.addEventListener("DOMContentLoaded", () => {
    const editProfileModal = document.getElementById("editProfileModal");
    if (!editProfileModal) return;

    window.openEditProfileModal = () => {
        editProfileModal.style.display = "flex";
        setTimeout(() => editProfileModal.classList.add("visible"), 10);
    };

    window.closeEditProfileModal = () => {
        editProfileModal.classList.remove("visible");
        setTimeout(() => editProfileModal.style.display = "none", 200);
    };

    editProfileModal.addEventListener("click", (e) => {
        if (e.target === editProfileModal) closeEditProfileModal();
    });
});

// ========== Create ønskeliste modal ==========
document.addEventListener("DOMContentLoaded", () => {
    const createWishlistModal = document.getElementById("createWishlistModal");
    if (!createWishlistModal) return;

    window.openCreateWishlistModal = () => {
        createWishlistModal.style.display = "flex";
        setTimeout(() => createWishlistModal.classList.add("visible"), 10);
    };

    window.closeCreateWishlistModal = () => {
        createWishlistModal.classList.remove("visible");
        setTimeout(() => createWishlistModal.style.display = "none", 200);
    };

    createWishlistModal.addEventListener("click", (e) => {
        if (e.target === createWishlistModal) closeCreateWishlistModal();
    });
});

// ========== Edit ønskeliste modal ==========
document.addEventListener("DOMContentLoaded", () => {
    window.openEditWishlistModal = (button) => {
        const modal = document.getElementById("editWishlistModal");
        const form = modal.querySelector("form");
        const nameInput = modal.querySelector("#edit-name");
        const descInput = modal.querySelector("#edit-description");

        // Udfyld felter med data
        nameInput.value = button.dataset.name;
        descInput.value = button.dataset.description;

        // Opdatér formens action
        const wishlistId = button.dataset.id;
        const profileId = form.getAttribute("data-profile-id");
        form.action = `/${profileId}/wishlists/${wishlistId}/edit`;

        modal.style.display = "flex";
        setTimeout(() => modal.classList.add("visible"), 10);
    };

    window.closeEditWishlistModal = () => {
        const modal = document.getElementById("editWishlistModal");
        modal.classList.remove("visible");
        setTimeout(() => modal.style.display = "none", 200);
    };
});
