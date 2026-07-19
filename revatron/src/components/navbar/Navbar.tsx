import useMediaQuery from "@/hooks/useMediaQuery";
import i18n from "@/utils/i18n/i18n";
import { useState } from "react";
import { useTranslation } from "react-i18next";
import { useNavigate } from "react-router-dom";
import { twMerge } from "tailwind-merge";
import ListIcon from "../icon/ListIcon";
import XIcon from "../icon/XIcon";
import { ROUTES } from "../router/routes.constant";
import NavbarLink from "./NavbarLink";

function Navbar() {
  const { t } = useTranslation();
  const [language, setIsLanguages] = useState("en");
  const isAboveMediumScreens = useMediaQuery("(min-width: 1060px)");
  const [isMenuToggled, setIsMenuToggled] = useState<boolean>(false);
  const navigate = useNavigate();

  const handleJapanese = () => {
    i18n.changeLanguage("ja");
    setIsLanguages("ja");
  };
  const handleEnglish = () => {
    i18n.changeLanguage("en");
    setIsLanguages("en");
  };

  return (
    <nav className='h-23 fixed top-0 z-20 flex w-full items-center justify-center bg-white px-10 py-6'>
      <div className='flex w-full items-center justify-between'>
        {/* Revatron */}
        <NavbarLink to={"/"}>
          <p className='text-xl font-bold text-warm-950'>Revatron</p>
        </NavbarLink>
        {/* Navlinks */}
        {isAboveMediumScreens ? (
          <div className='flex items-center justify-center gap-10'>
            {/* nav element */}
            <div className='flex items-center justify-center gap-5'>
              <NavbarLink to={ROUTES.ABOUT_US}>{t("navbar.about_us")}</NavbarLink>
              <NavbarLink to={ROUTES.EMULATED}>{t("navbar.emulated")}</NavbarLink>
              <NavbarLink to={ROUTES.THE_IMPACT}>{t("navbar.impact")}</NavbarLink>
              <NavbarLink to={ROUTES.PRODUCT}>{t("navbar.products")}</NavbarLink>
            </div>

            {/* contact us button */}
            <button
              onClick={() => navigate(ROUTES.CONTACT_US)}
              className='transform rounded bg-primary-500 px-6 py-2.5 text-base font-bold text-white transition duration-300 ease-in-out hover:scale-105 hover:shadow-xl'>
              Contact us
            </button>

            {/* translation */}
            <div className='flex items-center justify-center gap-1.5 text-xs font-bold'>
              <p
                onClick={handleJapanese}
                className={twMerge(
                  "cursor-pointer text-xs text-primary-600",
                  language === "ja" ? "text-primary-600" : "text-warm-400",
                )}>
                Ja
              </p>
              <span className='text-[0.5rem] text-warm-400'>/</span>
              <p
                onClick={handleEnglish}
                className={twMerge(
                  "cursor-pointer text-xs text-primary-600",
                  language === "en" ? "text-primary-600" : "text-warm-400",
                )}>
                En
              </p>
            </div>
          </div>
        ) : (
          <button onClick={() => setIsMenuToggled(!isMenuToggled)}>
            <ListIcon />
          </button>
        )}
      </div>
      {/* MOBILE MENU MODAL */}
      {!isAboveMediumScreens && isMenuToggled && (
        <div className={twMerge("fixed right-0 top-0 z-40 h-full w-[200px] bg-warm-25 drop-shadow-xl")}>
          {/* CLOSE ICON */}
          <div className='flex justify-end py-5 pl-4 pr-4'>
            <button onClick={() => setIsMenuToggled(!isMenuToggled)}>
              <XIcon className='h-6 w-6 text-gray-400' />
            </button>
          </div>

          {/* MENU ITEMS */}
          <div className='ml-[23%] mt-[10%] flex flex-col gap-10 text-lg'>
            <NavbarLink to={"/"}>
              <p className='text-xl font-bold text-warm-950'>Revatron</p>
            </NavbarLink>
            <NavbarLink to={ROUTES.ABOUT_US}>{t("navbar.about_us")}</NavbarLink>
            <NavbarLink to={ROUTES.EMULATED}>{t("navbar.emulated")}</NavbarLink>
            <NavbarLink to={ROUTES.THE_IMPACT}>{t("navbar.impact")}</NavbarLink>
            <NavbarLink to={ROUTES.PRODUCT}>{t("navbar.products")}</NavbarLink>
            <NavbarLink to={ROUTES.CONTACT_US}>{t("Contact Us")}</NavbarLink>

            <div className='flex items-center justify-start gap-1.5 text-xs font-bold'>
              <p
                onClick={handleJapanese}
                className={twMerge(
                  "cursor-pointer text-base text-primary-600",
                  language === "ja" ? "text-primary-600" : "text-warm-400",
                )}>
                Ja
              </p>
              <span className='text-[1rem] text-warm-400'>/</span>
              <p
                onClick={handleEnglish}
                className={twMerge(
                  "cursor-pointer text-base text-primary-600",
                  language === "en" ? "text-primary-600" : "text-warm-400",
                )}>
                En
              </p>
            </div>
          </div>
        </div>
      )}
    </nav>
  );
}

export default Navbar;
