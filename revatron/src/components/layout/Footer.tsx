import { useTranslation } from "react-i18next";
import NavbarLink from "../navbar/NavbarLink";
import { ROUTES } from "../router/routes.constant";
import FooterImage from "/images/footer-background.png";
export default function Footer() {
  const { t } = useTranslation();

  return (
    <div
      className='flex h-full w-full items-center justify-center bg-warm-950 pb-12 pt-16'
      style={{
        background: `url(${FooterImage})`,
        backgroundSize: "cover",
        backgroundPosition: "center ",
        backgroundRepeat: "no-repeat",
      }}>
      <div className='flex w-full max-w-[1184px] flex-col items-center justify-center gap-16 px-4'>
        {/* Link */}
        <div className='flex w-full'>
          <div className='flex w-full flex-wrap justify-between max-md:justify-start max-md:gap-6'>
            <div className='flex flex-col gap-10 max-md:gap-6'>
              <NavbarLink to={ROUTES.ABOUT_US} className='text-2xl font-normal text-warm-7 max-md:text-sm'>
                {t("navbar.about_us")}
              </NavbarLink>

              <NavbarLink to={ROUTES.EMULATED} className='text-2xl font-normal text-warm-7 max-md:text-nowrap max-md:text-sm'>
                {t("navbar.emulated")}
              </NavbarLink>
            </div>
            <div className='flex flex-col gap-10 max-md:gap-6'>
              <NavbarLink to={ROUTES.THE_IMPACT} className='text-2xl font-normal text-warm-7 max-md:text-sm'>
                {t("navbar.impact")}
              </NavbarLink>
              <NavbarLink to={ROUTES.PRODUCT} className='text-2xl font-normal text-warm-7 max-md:text-sm'>
                {t("navbar.products")}
              </NavbarLink>
            </div>
            <div className='flex flex-col gap-4'>
              <p className='text-2xl font-normal text-warm-7 max-md:text-sm'>support@revatron.com</p>
              <p className='text-2xl font-normal text-warm-7 max-md:text-sm'>+123 456 789</p>
            </div>
          </div>
        </div>
        {/* Copyright */}
        <div className='w-full'>
          <p className='text-2xl font-normal text-warm-7 max-md:text-sm'>@revatron. all rights reserved</p>
        </div>
      </div>
    </div>
  );
}
