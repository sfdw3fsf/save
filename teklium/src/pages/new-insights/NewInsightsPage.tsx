import PageContainer from '@/layouts/PageContainer';
import NewCard from './components/NewCard';
import data from './data/mockData';

export default function NewInsightsPage() {
  return (
    <PageContainer
      title='newAndInsights'
      isTitlePart
      isSubtitlePart
      mainTitle='News & Insights'
      subTitle='News & Insights'
      detailTitle='Stay updated with the latest developments at Teklium as well as in-depth insights into our technological advancements and industry impact.
'>
      <div className='flex flex-col gap-6 pt-28'>
        <input
          className='max-w-[320px] h-[48px] py-3 px-[14px] rounded-lg border border-slate-200 shadow-sm'
          placeholder='Search'
          type='search'></input>
        <section className='flex gap-6 '>
          {data.map((item) => (
            <NewCard
              key={item.partnerName}
              image={item.image}
              partnerIcon={item.partnerIcon}
              partnerName={item.partnerName}
              title={item.title}
              date={item.date}
              timeToRead={item.timeToRead}
            />
          ))}
        </section>
      </div>
    </PageContainer>
  );
}
