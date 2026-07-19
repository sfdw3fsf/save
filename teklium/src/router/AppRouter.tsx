// import React from 'react';

import LandingPage from '@/pages/landing-page/LandingPage';
import AboutUsPage from '@/pages/about-us/AboutUsPage';
import NewInsightsPage from '@/pages/new-insights/NewInsightsPage';
import { withLoading } from '@/components/hocs/withLoading';
import { BrowserRouter, Route, Routes } from 'react-router-dom';
import NavbarProvider from '@/components/navbar/NavbarProvider';

const LandingComponent = withLoading(LandingPage);
const AboutUsComponent = withLoading(AboutUsPage);
const NewInsightsComponent = withLoading(NewInsightsPage);

export default function AppRouter() {
  return (
    <BrowserRouter>
      <Routes>
        <Route element={<NavbarProvider />}>
          <Route path='/' index element={<LandingPage />} />
          <Route path='/aboutUs' element={<AboutUsComponent />} />
          <Route path='/newAndInsights' element={<NewInsightsComponent />} />
        </Route>
      </Routes>
    </BrowserRouter>
  );
}
