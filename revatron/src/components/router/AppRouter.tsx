import AboutUsPage from "@/pages/about-us/AboutUsPage";
import ContactPage from "@/pages/contact-us/ContactPage";
import EmulatedPage from "@/pages/emulated/EmulatedPage";
import LandingPage from "@/pages/landing-page/LandingPage";
import ProductPage from "@/pages/product/ProductPage";
import TheImpactPage from "@/pages/the-impact/TheImpactPage";
import ScrollToTop from "@/providers/ScrollToTop";
import { BrowserRouter, Route, Routes } from "react-router-dom";
import { withLoading } from "../hocs/withLoading";
import NavProvider from "../navbar/NavProvider";
import { ROUTES } from "./routes.constant";

function AppRouter() {
  const LandingComponent = withLoading(LandingPage);
  const AboutUSComponent = withLoading(AboutUsPage);
  const EmulatedComponent = withLoading(EmulatedPage);
  const TheImpactComponent = withLoading(TheImpactPage);
  const ProductComponent = withLoading(ProductPage);
  const ContactUsComponent = withLoading(ContactPage);

  return (
    <BrowserRouter>
      <ScrollToTop>
        <Routes>
          <Route element={<NavProvider />}>
            <Route path={ROUTES.self} index element={<LandingComponent />} />
            <Route path={ROUTES.ABOUT_US} element={<AboutUSComponent />} />
            <Route path={ROUTES.EMULATED} element={<EmulatedComponent />} />
            <Route path={ROUTES.THE_IMPACT} element={<TheImpactComponent />} />
            <Route path={ROUTES.PRODUCT} element={<ProductComponent />} />
            <Route path={ROUTES.CONTACT_US} element={<ContactUsComponent />} />
          </Route>
        </Routes>
      </ScrollToTop>
    </BrowserRouter>
  );
}

export default AppRouter;
