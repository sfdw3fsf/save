import Button from '@/components/core/button/Button';
import Divider from '@/components/core/divider/Divider';
import PageContainer from '@/components/core/page-container/PageContainer';
import StepLine, { Step } from '@/components/core/step-line/StepLine';
import ArrowLeftIcon from '@/components/icons/ArrowLeftIcon';
import { useBreakpoint } from '@/hooks/useBreakpoint';
import { ROUTES } from '@/router/routes.constant';
import { useMemo } from 'react';
import { useTranslation } from 'react-i18next';
import { useNavigate } from 'react-router-dom';
import { twMerge } from 'tailwind-merge';
import ControlButton from './ControlButton';
type ProjectRequestContainerProps = {
  children?: React.ReactNode;
  currentStep: number;
  form?: string;
  onPrevious: () => void;
  isShowStep?: boolean;
};
const ProjectRequestContainer = ({
  children,
  currentStep,
  form,
  onPrevious,
  isShowStep = true,
}: ProjectRequestContainerProps): JSX.Element => {
  const { hasMinWidthMd: isWeb } = useBreakpoint('md');
  const { t: tCommon } = useTranslation('common');
  const navigate = useNavigate();
  const steps: Step[] = useMemo(
    () => [
      {
        title: tCommon('project_request.step_1.title'),
        description: tCommon('project_request.step_1.description'),
      },
      {
        title: tCommon('project_request.step_2.title'),
        description: tCommon('project_request.step_2.description'),
      },
      {
        title: tCommon('project_request.step_3.title'),
        description: tCommon('project_request.step_3.description'),
      },
      {
        title: tCommon('project_request.step_4.title'),
        description: tCommon('project_request.step_4.description'),
      },
    ],
    [tCommon],
  );

  const renderInventoryButton = useMemo(() => {
    return (
      <Button
        leftIcon={<ArrowLeftIcon />}
        sizing={isWeb ? 'md' : 'sm'}
        onClick={() => navigate(ROUTES.PROJECT_MANAGEMENT.INDIVIDUAL)}>
        {tCommon('project_request.inventory')}
      </Button>
    );
  }, [isWeb, navigate, tCommon]);

  return (
    <PageContainer
      title={tCommon('project_request.title')}
      isFullScreen
      hiddenScroll
      className='items-start lg:px-28 px-4 pb-24 pt-12 max-lg:py-8 grid md:grid-cols-3 grid-cols-1 md:gap-8 gap-4'>
      {isShowStep && (
        <div className='col-span-1 flex flex-col md:gap-10 gap-4'>
          <div className='bg-white flex h-full rounded-lg border-[1px] border-slate-200 lg:px-3'>
            <StepLine steps={steps} currentStep={currentStep} variant={isWeb ? 'vertical' : 'horizontal'} />
          </div>
          {renderInventoryButton}
        </div>
      )}

      <div
        className={twMerge(
          'bg-white flex flex-col rounded-xl border-[1px] border-slate-200 p-8 max-md:p-4',
          isShowStep ? 'md:col-span-2 col-span-1' : 'col-span-3',
        )}>
        <div className='md:text-display-sm text-xl md:mb-6 mb-3 font-medium'>
          {tCommon('project_request.service_application_form')}
        </div>
        {children}
        {isShowStep && (
          <div className='flex flex-col gap-5 max-md:mt-6 mt-8'>
            <Divider />
            <ControlButton form={form} onPrevious={onPrevious} disablePreviousBtn={currentStep === 1} />
          </div>
        )}
      </div>
    </PageContainer>
  );
};

export default ProjectRequestContainer;
