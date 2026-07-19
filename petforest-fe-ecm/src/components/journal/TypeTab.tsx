import { JOURNAL_TEXT } from '@/utils/constant/customer-service/customer-service.constant';

type TypeTabProps = {
  tab: string;
  handleSelectTab: (tab: string) => void;
  selectedTab: string;
};

export const TypeTab = ({ tab, handleSelectTab, selectedTab }: TypeTabProps) => {
  return (
    <button
      key={tab}
      onClick={() => handleSelectTab(tab)}
      className={`px-4 py-1.5 mb-[1px] rounded-full border text-center transition duration-200 ease-in-out text-[15px] md:text-[16px] md:my-0
      ${
        selectedTab === tab
          ? 'bg-green-main text-white cursor-default'
          : 'bg-white text-green-main border-dark-green hover:bg-ligt-green'
      }
    `}
      disabled={selectedTab === tab}>
      {JOURNAL_TEXT[tab as keyof typeof JOURNAL_TEXT]}
    </button>
  );
};
