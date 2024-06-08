import React, { useState, useEffect } from "react";
import { useSelector } from "react-redux";
import axiosInstance from "../../utils/axiosInterceptors";
import ProjectForm from "../../components/ProjectForm/ProjectForm";
import ProjectList from "../../components/ProjectList/ProjectList";
import { TaskResponse, ProjectResponse } from "../../models/models";
import { Link } from "react-router-dom";
import { Pagination, Form, Button, Card } from "react-bootstrap";
import "./homepage.css";

const Homepage: React.FC = () => {
  const [projects, setProjects] = useState<ProjectResponse[]>([]);
  const [isAdded, setIsAdded] = useState<boolean>(false);
  const [expandedProjectId, setExpandedProjectId] = useState<number>(0);
  const [tasks, setTasks] = useState<TaskResponse[]>([]);
  const [currentPage, setCurrentPage] = useState<number>(0);
  const [pageSize, setPageSize] = useState<number>(10);
  const [totalPages, setTotalPages] = useState<number>(0);
  const [searchTerm, setSearchTerm] = useState<string>("");
  const [sortOrder, setSortOrder] = useState<string>("asc");

  const authState = useSelector((store: any) => store.auth);

  useEffect(() => {
    if (authState.id !== 0) {
      fetchProjects();
    }
  }, [authState.id, currentPage, pageSize, searchTerm, sortOrder]);

  useEffect(() => {
    if (isAdded && authState.id !== 0) {
      fetchProjects();
      setIsAdded(false);
    }
  }, [isAdded, authState.id]);

  const fetchProjects = async () => {
    try {
      const response = await axiosInstance.get(`/projects/page`, {
        params: { page: currentPage, size: pageSize, similarProjectName: searchTerm, sortOrder: sortOrder },
      });
      setTotalPages(response.data.totalPages);
      setProjects(response.data.content);
      console.log(response.data);
    } catch (error) {
      console.error("Projeler alınırken bir hata oluştu:", error);
    }
  };

  const handleExpandClick = async (projectId: number) => {
    if (expandedProjectId === projectId) {
      setExpandedProjectId(0);
    } else {
      try {
        fetchTasks(projectId);
        setExpandedProjectId(projectId);
      } catch (error) {
        console.error('Görevleri yüklerken hata oluştu:', error);
      }
    }
  };

  const fetchTasks = async (projectId: number, status?: string) => {
    if (!status || status?.length < 3) {
      const response = await axiosInstance.get<TaskResponse[]>(`/tasks/project-tasks/${projectId}`);
      setTasks(response.data);
    } else {
      const response = await axiosInstance.get<TaskResponse[]>(`/tasks/sort/${status}/${projectId}`);
      setTasks(response.data);
    }
  };

  return (
    <div className="container mt-5">
    {authState.id === 0 ? (
      <div className="text-center login-prompt">
        <h1>Giriş Yapılmadı</h1>
        <p>Proje ve Görev Yönetim Sistemi'ni kullanmak için lütfen giriş yapın.</p>
        <Link to="/login" className="btn btn-primary login-button">
          Giriş Yap
        </Link>
      </div>
    ) : (
      <>
        <h1 className="text-center">Proje ve Görev Yönetimi</h1>
        <div className="row">
          <div className="col-md-8">
            <ProjectForm setIsAdded={setIsAdded} />
          </div>
          <div className="col-md-4 align-self-end">
            <Card>
              <Card.Body>
                <Card.Title>Proje Ara ve Filtrele</Card.Title>
                <Form onSubmit={(e) => { e.preventDefault(); fetchProjects(); }}>
                  <div className="row">
                    <div className="col-md-6">
                      <Form.Group controlId="searchTerm">
                        <Form.Label>Proje Adı Benzerleri</Form.Label>
                        <Form.Control 
                          type="text" 
                          placeholder="Proje adını girin" 
                          value={searchTerm} 
                          onChange={(e) => setSearchTerm(e.target.value)} 
                          className="mb-3"
                        />
                      </Form.Group>
                    </div>
                    <div className="col-md-6">
                      <Form.Group controlId="sortOrder">
                        <Form.Label>Tarih Sıralama </Form.Label>
                        <Form.Control 
                          as="select" 
                          value={sortOrder} 
                          onChange={(e) => setSortOrder(e.target.value)}
                          className="mb-3"
                        >
                          <option value="asc">Artan </option>
                          <option value="desc">Azalan </option>
                        </Form.Control>
                      </Form.Group>
                    </div>
              
                  </div>
                </Form>
              </Card.Body>
            </Card>
          </div>
        </div>
        <div className="mt-5">
          <h2>Projeler ve Görevler</h2>
          <ProjectList 
            fetchTasks={fetchTasks}
            projects={projects} 
            tasks={tasks} 
            expandedProjectId={expandedProjectId} 
            handleExpandClick={handleExpandClick} 
          />
        </div>
        <Pagination>
          {[...Array(totalPages)].map((_, index) => (
            <Pagination.Item 
              key={index} 
              active={index === currentPage} 
              onClick={() => setCurrentPage(index)}
            >
              {index + 1}
            </Pagination.Item>
          ))}
        </Pagination>
      </>
    )}
  </div>
  
  
  
  

  );
};

export default Homepage;
