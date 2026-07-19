import { NavbarWrapper } from '@/components/core/navbar-wrapper/NavbarWrapper';
import { withLoading } from '@/components/hocs/withLoading';
import { BrowserRouter, Navigate, Route, Routes } from 'react-router-dom';
import AppRoute from './AppRoute';
import AuthRoute from './AuthRoute';
import NavRoute from './NavRoute';
import { NAVIGATION_ROUTES, ROUTES } from './routes.constant';
import UnauthRoute from './UnauthRoute';

import ChangePasswordNavigation from '@/pages/change-password/ChangePasswordNavigation';
import SignUpStep3Navigation from '@/pages/sign-up/components/SignUpStep3Navigation';

import AnnouncementPage from '@/pages/announcement/AnnouncementPage';
import AnnouncementDetailPage from '@/pages/announcement/detail/AnnouncementDetailPage';
import ChangePasswordPage from '@/pages/change-password/ChangePasswordPage';
import FaqPage from '@/pages/faq/FaqPage';
import FindIdPage from '@/pages/find-id/FindIdPage';
import FindPasswordPage from '@/pages/find-password/FindPasswordPage';
import FindPage from '@/pages/find/FindPage';
import HomePage from '@/pages/home/HomePage';
import InquiryDetailPage from '@/pages/inquiry/detail/InquiryDetailPage';
import InquiryPage from '@/pages/inquiry/InquiryPage';
import LoginPage from '@/pages/login/LoginPage';
import InfoExpertPage from '@/pages/my-page/components/InfoExpertPage';
import InfoIndivPage from '@/pages/my-page/components/InfoIndivPage';
import PasswordPage from '@/pages/my-page/components/PasswordPage';
import ScheduleExpertPage from '@/pages/my-page/components/ScheduleExpertPage';
import ScheduleIndivPage from '@/pages/my-page/components/ScheduleIndivPage';
import MyPage from '@/pages/my-page/MyPage';
import NewsDetailPage from '@/pages/news/detail/NewsDetailPage';
import NewsPage from '@/pages/news/detail/NewsPage';
import NotFoundPage from '@/pages/not-found/NotFoundPage';
import ProjectDetailExpertPage from '@/pages/project-management/detail/ProjectDetailExpertPage';
import ProjectDetailIndivPage from '@/pages/project-management/detail/ProjectDetailIndivPage';
import ExpertMatchingListPage from '@/pages/project-management/expert-matching-list/ExpertMatchingListPage';
import ProjectManagementExpertPage from '@/pages/project-management/project-management-expert/ProjectManagementExpertPage';
import ProjectManagementIndivPage from '@/pages/project-management/project-management-indiv/ProjectManagementIndivPage';
import ProjectRequestIndivPage from '@/pages/project-request/project-request-indiv/ProjectRequestIndivPage';
import SignUpExpertStep1Page from '@/pages/sign-up-expert/SignUpExpertStep1Page';
import SignUpExpertStep2Page from '@/pages/sign-up-expert/SignUpExpertStep2Page';
import SignUpExpertStep3Page from '@/pages/sign-up-expert/SignUpExpertStep3Page';
import SignUpIndivStep1Page from '@/pages/sign-up-indiv/SignUpIndivStep1Page';
import SignUpIndivStep2Page from '@/pages/sign-up-indiv/SignUpIndivStep2Page';
import SignUpIndivStep3Page from '@/pages/sign-up-indiv/SignUpIndivStep3Page';
import SignUpPage from '@/pages/sign-up/SignUpPage';

const AnnouncementComponent = withLoading(AnnouncementPage);
const AnnouncementDetailComponent = withLoading(AnnouncementDetailPage);
const ChangePasswordComponent = withLoading(ChangePasswordPage);
const FaqComponent = withLoading(FaqPage);
const FindIdComponent = withLoading(FindIdPage);
const FindPasswordComponent = withLoading(FindPasswordPage);
const FindComponent = withLoading(FindPage);
const HomeComponent = withLoading(HomePage);
const InquiryDetailComponent = withLoading(InquiryDetailPage);
const InquiryComponent = withLoading(InquiryPage);
const LoginComponent = withLoading(LoginPage);
const InfoExpertComponent = withLoading(InfoExpertPage);
const InfoIndivComponent = withLoading(InfoIndivPage);
const PasswordComponent = withLoading(PasswordPage);
const ScheduleExpertComponent = withLoading(ScheduleExpertPage);
const ScheduleIndivComponent = withLoading(ScheduleIndivPage);
const NewsDetailComponent = withLoading(NewsDetailPage);
const NewsComponent = withLoading(NewsPage);
const NotFoundComponent = withLoading(NotFoundPage);
const ProjectDetailExpertComponent = withLoading(ProjectDetailExpertPage);
const ProjectDetailIndivComponent = withLoading(ProjectDetailIndivPage);
const ExpertMatchingListComponent = withLoading(ExpertMatchingListPage);
const ProjectManagementExpertComponent = withLoading(ProjectManagementExpertPage);
const ProjectManagementIndivComponent = withLoading(ProjectManagementIndivPage);
const ProjectRequestIndivComponent = withLoading(ProjectRequestIndivPage);
const SignUpExpertStep1Component = withLoading(SignUpExpertStep1Page);
const SignUpExpertStep2Component = withLoading(SignUpExpertStep2Page);
const SignUpExpertStep3Component = withLoading(SignUpExpertStep3Page);
const SignUpIndivStep1Component = withLoading(SignUpIndivStep1Page);
const SignUpIndivStep2Component = withLoading(SignUpIndivStep2Page);
const SignUpIndivStep3Component = withLoading(SignUpIndivStep3Page);
const SignUpComponent = withLoading(SignUpPage);
const MyPageComponent = withLoading(MyPage);

const AppRouter = () => {
  return (
    <BrowserRouter>
      <Routes>
        <Route element={<NavbarWrapper />}>
          <Route element={<AppRoute />}>
            <Route element={<UnauthRoute />}>
              <Route path={ROUTES.LOGIN} element={<LoginComponent />} />

              <Route path={ROUTES.FIND.self} element={<FindComponent />} />
              <Route path={ROUTES.FIND.ID} element={<FindIdComponent />} />
              <Route path={ROUTES.FIND.PASSWORD} element={<FindPasswordComponent />} />

              <Route path={ROUTES.SIGN_UP.self} element={<SignUpComponent />} />
              <Route path={ROUTES.SIGN_UP.INDIVIDUAL.self} element={<Navigate to={ROUTES.SIGN_UP.INDIVIDUAL.STEP_1} replace />} />
              <Route path={ROUTES.SIGN_UP.INDIVIDUAL.STEP_1} element={<SignUpIndivStep1Component />} />
              <Route path={ROUTES.SIGN_UP.INDIVIDUAL.STEP_2} element={<SignUpIndivStep2Component />} />
              <Route path={ROUTES.SIGN_UP.EXPERT.self} element={<Navigate to={ROUTES.SIGN_UP.EXPERT.STEP_1} replace />} />
              <Route path={ROUTES.SIGN_UP.EXPERT.STEP_1} element={<SignUpExpertStep1Component />} />
              <Route path={ROUTES.SIGN_UP.EXPERT.STEP_2} element={<SignUpExpertStep2Component />} />
            </Route>
            <Route>
              <Route element={<AuthRoute />}>
                <Route path={ROUTES.SIGN_UP.INDIVIDUAL.STEP_3} element={<SignUpIndivStep3Component />} />
                <Route path={ROUTES.SIGN_UP.EXPERT.STEP_3} element={<SignUpExpertStep3Component />} />

                <Route path={ROUTES.PROJECT_MANAGEMENT.INDIVIDUAL} element={<ProjectManagementIndivComponent />} />
                <Route path={ROUTES.PROJECT_MANAGEMENT.EXPERT} element={<ProjectManagementExpertComponent />} />
                <Route path={ROUTES.PROJECT_REQUEST.INDIVIDUAL.REQUEST} element={<ProjectRequestIndivComponent />} />
                <Route path={ROUTES.PROJECT_REQUEST.INDIVIDUAL.EDIT} element={<ProjectRequestIndivComponent />} />
                <Route path={ROUTES.PROJECT_MANAGEMENT.DETAIL.EXPERT} element={<ProjectDetailExpertComponent />} />
                <Route path={ROUTES.PROJECT_MANAGEMENT.DETAIL.INDIVIDUAL} element={<ProjectDetailIndivComponent />} />
                <Route path={ROUTES.PROJECT_MANAGEMENT.EXPERT_MATCHING_LIST} element={<ExpertMatchingListComponent />} />

                <Route path={ROUTES.PROJECT_MANAGEMENT.ACCEPT_MATCH} element={<ProjectManagementExpertComponent />} />

                <Route path={ROUTES.ACCOUNT.CHANGE_PASSWORD} element={<ChangePasswordComponent />} />
                <Route path={ROUTES.MY_PAGE.self} element={<MyPageComponent />} />
                <Route
                  path={ROUTES.MY_PAGE.INDIVIDUAL.self}
                  element={<Navigate to={ROUTES.MY_PAGE.INDIVIDUAL.SCHEDULE} replace />}
                />
                <Route path={ROUTES.MY_PAGE.INDIVIDUAL.SCHEDULE} element={<ScheduleIndivComponent />} />
                <Route path={ROUTES.MY_PAGE.EXPERT.self} element={<Navigate to={ROUTES.MY_PAGE.EXPERT.SCHEDULE} replace />} />
                <Route path={ROUTES.MY_PAGE.EXPERT.SCHEDULE} element={<ScheduleExpertComponent />} />
                <Route path={ROUTES.MY_PAGE.INDIVIDUAL.INFO} element={<InfoIndivComponent />} />
                <Route path={ROUTES.MY_PAGE.EXPERT.INFO} element={<InfoExpertComponent />} />
                <Route path={ROUTES.MY_PAGE.INDIVIDUAL.PASSWORD} element={<PasswordComponent />} />
                <Route path={ROUTES.MY_PAGE.EXPERT.PASSWORD} element={<PasswordComponent />} />
              </Route>
            </Route>

            <Route element={<NavRoute />}>
              <Route path={NAVIGATION_ROUTES.SIGN_UP_STEP_3} element={<SignUpStep3Navigation />} />
              <Route path={NAVIGATION_ROUTES.CHANGE_PASSWORD} element={<ChangePasswordNavigation />} />
            </Route>

            <Route path={ROUTES.self} element={<Navigate to={ROUTES.HOME} replace />} />
            <Route path={ROUTES.HOME} element={<HomeComponent />} />

            <Route path={ROUTES.FAQ} element={<FaqComponent />} />

            <Route path={ROUTES.NEWS.self} element={<NewsComponent />} />
            <Route path={ROUTES.NEWS.DETAIL} element={<NewsDetailComponent />} />

            <Route path={ROUTES.INQUIRY.self} element={<InquiryComponent />} />
            <Route path={ROUTES.INQUIRY.DETAIL} element={<InquiryDetailComponent />} />

            <Route path={ROUTES.ANNOUNCEMENT.self} element={<AnnouncementComponent />} />
            <Route path={ROUTES.ANNOUNCEMENT.DETAIL} element={<AnnouncementDetailComponent />} />

            <Route path='*' element={<NotFoundComponent />} />
          </Route>
        </Route>
      </Routes>
    </BrowserRouter>
  );
};

export default AppRouter;
