import PageContainer from "@/components/layout/PageContainer";
import AboutUsSection from "./components/AboutUsSection";
import Coporate from "./components/Coporate";
import EmulatedQuantum from "./components/EmulatedQuantum";
import Overview from "./components/Overview";
import PeopleSay from "./components/PeopleSay";
import ProductSection from "./components/ProductSection";
import TheImpactSection from "./components/TheImpactSection";
import WhatIsQauntumSection from "./components/WhatIsQauntumSection";
import WhyQuantumSection from "./components/WhyQuantumSection";

function LandingPage() {
  return (
    <PageContainer>
      <div className='flex w-full flex-col items-center justify-center px-4'>
        {/* <OverviewSection /> */}
        <Overview />
        <AboutUsSection />
      </div>
      <WhatIsQauntumSection />
      <WhyQuantumSection />
      <EmulatedQuantum />
      <TheImpactSection />
      <ProductSection />
      <PeopleSay />
      <div className='flex w-full flex-col items-center justify-center px-4'>
        <Coporate />
      </div>
    </PageContainer>
  );
}

export default LandingPage;
