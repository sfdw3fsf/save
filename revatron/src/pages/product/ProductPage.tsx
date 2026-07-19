import ContactForm from "@/components/contact-form/ContactForm";
import IntroductionSection from "@/components/introduction/IntroductionSection";
import PageContainer from "@/components/layout/PageContainer";
import { useTranslation } from "react-i18next";

function ProductPage() {
  const { t } = useTranslation("");
  const tittleImage = "/images/tittle/products.png";

  return (
    <PageContainer isTittleImage tittle={t("product.page_tittle")} image={tittleImage}>
      <IntroductionSection
        tittle_one={t("product.card_1_tittle")}
        content_one={t("product.card_1_content")}
        image_one={"/images/product/product_1.png"}
        tittle_two={t("product.card_2_tittle")}
        content_two={t("product.card_2_content")}
        image_two={"/images/product/product_2.png"}
      />
      <ContactForm />
    </PageContainer>
  );
}

export default ProductPage;
