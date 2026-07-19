import { useTranslation } from 'react-i18next';
type TitleProps = {
  title?: string;
  subTitle: string;
};
const Title = ({ title, subTitle }: TitleProps): JSX.Element => {
  const { t: tCommon } = useTranslation('common');
  return (
    <div className='flex flex-col'>
      <h1 className='text-display-sm font-medium text-comp-text-default'>
        {title ? title : tCommon('project_management.title')}
      </h1>
      <p className='text-md font-normal my-2 text-comp-text-secondary'>{subTitle}</p>
    </div>
  );
};
export default Title;
